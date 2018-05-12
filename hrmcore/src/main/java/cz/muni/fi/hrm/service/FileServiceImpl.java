package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ErrorMarginDTO;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Inject
    private RefCurveRepository refCurveRepository;

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public List<RefCurveDTO> readUploadedFile(MultipartFile file, boolean marginErrorSheet) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("Cannot read file that is null");
        }
        logger.debug("start to read uploaded file", file.getOriginalFilename());
        String filename = file.getOriginalFilename();
        List<RefCurveDTO> curves = null;
        if (filename.endsWith(".csv")) {
            if (marginErrorSheet) {
                throw new IllegalArgumentException(".csv files are not supported, add excel file - .xlsx or xls ");
            }
            curves = this.parseDataFromCsv(file);
        } else if (filename.endsWith(".xlsx")) {
            curves = this.parseDataFromXlsx(file, marginErrorSheet);
        } else if (filename.endsWith(".xls")) {
            curves = this.parseDataFromXls(file, marginErrorSheet);
        } else {
            throw new IllegalArgumentException("Unknown file type");
        }
        if (curves == null || curves.size() == 0 || curves.get(0) == null) {
            throw new IllegalArgumentException("Reading of file was not successful or file is empty");
        }
        checkCurvesFormat(curves);
        if (this.checkIfCurveIsTemperature(curves.get(0))) {
            curves.get(0).setName("temperature");
            if (!marginErrorSheet) {
                curves = this.checkIntervalOfData(curves,
                        refCurveRepository.findTemperature().getValues().size());
                if ("temperature".equals(curves.get(0).getName())) {
                    curves.remove(0); //we dont need temperature no more after adjusting data
                }
            }else{
                checkIfMarginIsPresent(curves);
            }
        }else if(marginErrorSheet){
            throw new IllegalArgumentException("First collumn is not temperature, please add temperature values");
        }
        logger.debug("uploaded file read", file.getOriginalFilename());
        return curves;
    }

    /**
     * method checks if for every curve is margin that has same number of points
     */
    private void checkIfMarginIsPresent(List<RefCurveDTO> curves) {
        for(RefCurveDTO curve : curves){
            if(!"temperature".equals(curve.getName())){
                if(curve.getErrorMargin() == null || curve.getValues() == null ||
                        curve.getValues().size() != curve.getErrorMargin().getValues().size()){
                    throw new IllegalArgumentException("for curve "+curve.getName()+ " there is different number of values and error margin values");
                }
            }
        }
    }

    /**
     * checks if curves are in appropriate format otherwise throw exception to show error on frontend
     */
    private void checkCurvesFormat(List<RefCurveDTO> curves) {
        int valuesCount = 0;
        for (int i = 0; i < curves.size(); ++i) {
            if (curves.get(i) == null) {
                throw new IllegalArgumentException("error while reading " + i + ". collumn");
            }
            if (curves.get(i).getName() == null) {
                throw new IllegalArgumentException("no name found for curve in " + i + ". collumn");
            }
            int curveSize = curves.get(i).getValues().size();
            if (curveSize == 0) {
                throw new IllegalArgumentException("No values found for user data set in " + i + ". collumn");
            }
            if (i == 0) {
                valuesCount = curveSize;
            } else if (curveSize != valuesCount) {
                throw new IllegalArgumentException("Every user data set should be same length as the others");
            }
        }
    }

    @Override
    public HSSFWorkbook generateFileOfDbData() throws IOException {
        logger.debug("start generating data from db");
        RefCurve temperature = refCurveRepository.findTemperature();
        List<RefCurve> refCurves = refCurveRepository.findAll();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Reference curves");

        this.putHeaderInSheet(sheet, temperature, refCurves);
        this.putValuesInSheet(sheet, temperature, refCurves);

        HSSFSheet sheet2 = workbook.createSheet("Error margins");
        this.putMarginErrorsInSheet(sheet2, refCurves, temperature.getValues().size());


        try (FileOutputStream outputStream = new FileOutputStream("data.xls")) {
            workbook.write(outputStream);
        }
        logger.debug("data generated to xls from db");
        return workbook;
    }

    /**
     * return curves that are defined on whole interval of temperature that we have in db
     */
    private List<RefCurveDTO> checkIntervalOfData(List<RefCurveDTO> curves, int max) {
        RefCurveDTO temperature = curves.get(0);
        if (max < temperature.getValues().size()) {
            curves = this.adjustBiggerInterval(curves);
        }
        int index = this.indexOfSubinterval(temperature);
        if (index != -1) {
            for (RefCurveDTO curve : curves) {
                List<Double> newValues = new ArrayList<>();
                for (int j = 0; j < index; ++j) {
                    newValues.add(null);
                }
                newValues.addAll(curve.getValues());
                while (newValues.size() < max) {
                    newValues.add(null);
                }
                curve.setValues(newValues);
            }
        } else {
            curves = this.interpolateData(curves);
        }
        return curves;
    }

    /**
     * if user temperature is in wider range that temperature in db, so user data are removed on
     * temperature that we cannot use
     */
    private List<RefCurveDTO> adjustBiggerInterval(List<RefCurveDTO> curves) {
        RefCurve dbTemperature = refCurveRepository.findTemperature();
        Double min = dbTemperature.getValues().get(0);
        Double max = dbTemperature.getValues().get(dbTemperature.getValues().size() - 1);
        for (int i = 0; i < curves.get(0).getValues().size(); ++i) {
            if (curves.get(0).getValues().get(i) < min || curves.get(0).getValues().get(i) > max) {
                for (RefCurveDTO curve : curves) {
                    curve.getValues().remove(i);
                }
            }
        }
        return curves;
    }

    /**
     * interpolates data to get data, that are on temperature that we have in DB
     */
    private List<RefCurveDTO> interpolateData(List<RefCurveDTO> curves) {
        RefCurve dbTemperature = refCurveRepository.findTemperature();
        RefCurveDTO userTemperature = curves.get(0);
        List<RefCurveDTO> newCurves = new ArrayList<>();

        for (int j = 1; j < curves.size(); ++j) {
            RefCurveDTO newCurve = new RefCurveDTO();
            int temperatureIndex = 0;
            boolean noMoreTemperatureValues = false;
            for (int i = 0; i < dbTemperature.getValues().size(); ++i) {
                if (noMoreTemperatureValues) {
                    newCurve.getValues().add(null);
                    continue;
                }
                if (temperatureIndex == userTemperature.getValues().size() - 1) {
                    noMoreTemperatureValues = true;
                    newCurve.getValues().add(null);
                    continue;
                }
                Double userTemperatureValue = userTemperature.getValues().get(temperatureIndex);
                if (Math.abs(dbTemperature.getValues().get(i) - userTemperatureValue) <= 0.00000001) {
                    newCurve.getValues().add(curves.get(j).getValues().get(temperatureIndex));
                } else if (dbTemperature.getValues().get(i) < userTemperatureValue) {
                    newCurve.getValues().add(null);
                } else if (userTemperatureValue < dbTemperature.getValues().get(i) &&
                        dbTemperature.getValues().get(i) < userTemperature.getValues().get(temperatureIndex + 1)) {
                    Double x0 = userTemperatureValue;
                    Double y0 = curves.get(j).getValues().get(temperatureIndex);
                    Double x1 = userTemperature.getValues().get(temperatureIndex + 1);
                    Double y1 = curves.get(j).getValues().get(temperatureIndex + 1);
                    Double x = dbTemperature.getValues().get(i);
                    Double value = this.interpolate(x0, x1, y0, y1, x);
                    newCurve.getValues().add(value);
                } else {
                    ++temperatureIndex;
                    --i;
                }
            }
            newCurves.add(newCurve);
        }
        return newCurves;
    }

    private Double interpolate(Double x0, Double x1, Double y0, Double y1, Double x) {
        if (x1 - x0 == 0.0) return 0.0;
        return ((y0 * (x1 - x)) + (y1) * (x - x0)) / (x1 - x0);
    }

    /**
     * find if user temperature is subinterval of temperature that is in database
     */
    private int indexOfSubinterval(RefCurveDTO temperature) {
        RefCurve dbTemperature = refCurveRepository.findTemperature();
        int index = 0;
        int ret = -1;
        boolean sequence = false;
        for (int i = 0; i < dbTemperature.getValues().size(); ++i) {
            if (index == temperature.getValues().size()) {
                break;
            }
            Double dbValue = dbTemperature.getValues().get(i);
            if (Math.abs(temperature.getValues().get(index) - dbValue) <= 0.00000001) {
                if (!sequence) {
                    sequence = true;
                    ret = i;
                    index++;
                } else {
                    index++;
                }
            } else {
                if (sequence) {
                    break;
                }
            }
        }
        if (index == temperature.getValues().size()) {
            return ret;
        }
        return -1;
    }

    /**
     * curve is temperature if all values fit into interval (Temperature_min, temperature_max)
     */
    private boolean checkIfCurveIsTemperature(RefCurveDTO refCurveDTO) {
        List<Double> values = refCurveDTO.getValues();
        Double previous = 0.0;
        for (int i = 0; i < values.size(); ++i) {
            if (values.get(i) < TEMPERATURE_MIN || values.get(i) > TEMPERATURE_MAX ||
                    values.get(i) < previous) {
                return false;
            }
            previous = values.get(i);
        }
        return true;
    }

    private void putMarginErrorsInSheet(HSSFSheet sheet2, List<RefCurve> refCurves, int size) {
        //header
        Row row = sheet2.createRow(0);
        for (int i = 0; i < refCurves.size(); ++i) {
            Cell c = row.createCell(i);
            c.setCellValue(refCurves.get(i).getName());
        }
        //values
        for (int i = 0; i < size; ++i) {
            row = sheet2.createRow(i + 1); //+1 because of a header
            for (int j = 0; j < refCurves.size(); ++j) {
                Cell cell = row.createCell(j);
                cell.setCellValue(refCurves.get(j).getErrorMargin().getValues().get(i));
            }
        }
    }

    /**
     * adds values of reference curves to generated excel workbook
     */
    private void putValuesInSheet(HSSFSheet sheet, RefCurve temperature, List<RefCurve> refCurves) {
        for (int i = 0; i < temperature.getValues().size(); ++i) {
            Row row = sheet.createRow(i + 3); //+3 because of a header
            Cell cellTemperature = row.createCell(0);
            cellTemperature.setCellValue(temperature.getValues().get(i));
            for (int j = 0; j < refCurves.size(); ++j) {
                Cell cell = row.createCell(j + 1);
                cell.setCellValue(refCurves.get(j).getValues().get(i));
            }
        }
    }

    /**
     * adds header to generated excel workbook
     */
    private void putHeaderInSheet(HSSFSheet sheet, RefCurve temperature, List<RefCurve> refCurves) {
        //names
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(temperature.getName());
        for (int i = 0; i < refCurves.size(); ++i) {
            Cell c = row.createCell(i + 1);
            c.setCellValue(refCurves.get(i).getName());
        }
        //acronyms
        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(temperature.getAcronym());
        for (int i = 0; i < refCurves.size(); ++i) {
            Cell c = row.createCell(i + 1);
            c.setCellValue(refCurves.get(i).getAcronym());
        }
        //notes
        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(temperature.getNote());
        for (int i = 0; i < refCurves.size(); ++i) {
            Cell c = row.createCell(i + 1);
            c.setCellValue(refCurves.get(i).getNote());
        }
    }

    private List<RefCurveDTO> parseDataFromCsv(final MultipartFile file) throws IOException {
        boolean firstRow = true;
        List<RefCurveDTO> curves = null;
        final Reader reader = new InputStreamReader(file.getInputStream());
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT);
        try {
            for (CSVRecord record : parser) {
                if (firstRow) {
                    curves = this.checkCsvHeader(record);
                    firstRow = false;
                    continue;
                }
                for (int i = 0; i < record.size(); ++i) {
                    Double parsed = null;
                    try {
                        parsed = Double.parseDouble(record.get(i));
                        curves.get(i).values.add(parsed);
                    } catch (Exception e) {
                        logger.error("could not parse ", record.get(i), e);
                        throw new IllegalArgumentException("found data that are not in number format, collumn " + i);
                    }
                }
            }
        } finally {
            parser.close();
        }
        return curves;
    }

    private List<RefCurveDTO> parseDataFromXlsx(final MultipartFile file, boolean marginErrorSheet) throws IOException {
        List<RefCurveDTO> ret = null;
        try (Workbook wb = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            ret = this.iterateExcelSheet(iterator, marginErrorSheet);
            List<ErrorMarginDTO> errorMargins = null;
            if (marginErrorSheet) {
                errorMargins = this.parseErrorMargins(file);
                ret = this.connectMarginsToCurves(ret, errorMargins);
            }
        } catch (IOException e) {
            logger.error("could not read file ", file.getName(), e);
            throw new IllegalArgumentException(file.getName() + " cannot read this file.");
        }
        return ret;
    }

    private List<RefCurveDTO> parseDataFromXls(MultipartFile file, boolean marginErrorSheet) {
        List<RefCurveDTO> ret = null;
        try (HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream())) {

            HSSFSheet sheet = wb.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();

            ret = this.iterateExcelSheet(iterator, marginErrorSheet);
            List<ErrorMarginDTO> errorMargins = null;
            if (marginErrorSheet) {
                errorMargins = this.parseErrorMargins(file);
                ret = this.connectMarginsToCurves(ret, errorMargins);
            }
        } catch (IOException e) {
            logger.error("could not read file ", file.getName(), e);
            throw new IllegalArgumentException(file.getName() + " cannot read this file.");
        }
        return ret;
    }

    private List<ErrorMarginDTO> parseErrorMargins(MultipartFile file) {
        List<ErrorMarginDTO> ret = null;
        try (HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream())) {

            HSSFSheet sheet = wb.getSheetAt(1);
            Iterator<Row> iterator = sheet.iterator();

            int i = 0;
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                if (i == 0) {
                    ret = checkExcelHeaderErrorMargin(cellIterator);
                    ++i;
                    continue;
                }
                int cellCounter = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellTypeEnum()) {
                        case NUMERIC:
                            ret.get(cellCounter).values.add(cell.getNumericCellValue());
                            break;
                        case STRING:
                        case BOOLEAN:
                            throw new IllegalArgumentException("found value that was not numeric and is not allowed");
                    }
                    cellCounter++;
                }
                ++i;
            }
        } catch (IOException e) {
            logger.error("could not read file ", file.getName(), e);
            throw new IllegalArgumentException(file.getName() + " cannot read this file.");
        }
        return ret;

    }

    private List<RefCurveDTO> iterateExcelSheet(Iterator<Row> iterator, boolean marginErrorSheet) {
        List<RefCurveDTO> curves = null;
        int i = 0;
        while (iterator.hasNext()) {
            if (i == 0) {
                if (marginErrorSheet) {
                    curves = this.checkExcelHeaderWithAllValues(iterator);
                    ++i;
                    continue;
                }
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                curves = this.checkExcelHeader(cellIterator);
                ++i;
                continue;
            }
            if (i < 3 && marginErrorSheet) {
                ++i;
                continue;
            }
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int cellCounter = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cellCounter >= curves.size()) {
                    throw new IllegalArgumentException("Format violation - there is more cells in values row " + i);
                }
                switch (cell.getCellTypeEnum()) {
                    case NUMERIC:
                        curves.get(cellCounter).values.add(cell.getNumericCellValue());
                        break;
                    case STRING:
                    case BOOLEAN:
                        throw new IllegalArgumentException("found value that was not numeric and is not allowed");
                }
                cellCounter++;
            }
            ++i;
        }
        return curves;
    }

    private List<RefCurveDTO> checkCsvHeader(CSVRecord record) {
        List<RefCurveDTO> curves = new ArrayList<>();
        for (int i = 0; i < record.size(); ++i) {
            RefCurveDTO curve = new RefCurveDTO();
            Double parsed = null;
            try {
                parsed = Double.parseDouble(record.get(i));
                curve.values.add(parsed);
            } catch (Exception e) {
                curve.name = record.get(i);
            }
            curves.add(curve);
        }
        return curves;
    }

    private List<RefCurveDTO> checkExcelHeader(Iterator<Cell> cellIterator) {
        int i = 0;
        List<RefCurveDTO> ret = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            RefCurveDTO curve = new RefCurveDTO();
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    curve.name = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    curve.name = "user_curve_" + i;
                    curve.values.add(cell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    break;
            }
            if (curve.name != null || !curve.getValues().isEmpty()) {
                ret.add(curve);
            }
            ++i;
        }
        return ret;
    }

    private List<ErrorMarginDTO> checkExcelHeaderErrorMargin(Iterator<Cell> cellIterator) {
        List<ErrorMarginDTO> ret = new ArrayList<>();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            ErrorMarginDTO errorMargin = new ErrorMarginDTO();
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    errorMargin.name = cell.getStringCellValue();
                    break;
                case NUMERIC:
                case BOOLEAN:
                    return null;
            }
            ret.add(errorMargin);
        }
        return ret;
    }

    /**
     * checks and returns header of data that should be added to DB
     */
    private List<RefCurveDTO> checkExcelHeaderWithAllValues(Iterator<Row> iterator) {
        int index = 0;
        List<RefCurveDTO> ret = new ArrayList<>();
        while (iterator.hasNext() && index < 3) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            int i = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                RefCurveDTO curve = null;
                if (index == 0) {
                    curve = new RefCurveDTO();
                } else {
                    curve = ret.get(i);
                }
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        String str = cell.getStringCellValue();
                        switch (index) {
                            case 0:
                                curve.name = str;
                                break;
                            case 1:
                                curve.acronym = str;
                                break;
                            case 2:
                                curve.note = str;
                        }
                        break;
                    case NUMERIC:
                    case BOOLEAN:
                        break;
                }
                if (index == 0) {
                    ret.add(curve);
                }
                ++i;
            }
            index++;
        }

        return ret;

    }

    private List<RefCurveDTO> connectMarginsToCurves(List<RefCurveDTO> curves, List<ErrorMarginDTO> errorMargins) {
        for (RefCurveDTO curve : curves) {
            String name = curve.getName();
            for (ErrorMarginDTO margin : errorMargins) {
                if (name.equals(margin.name)) {
                    curve.setErrorMargin(margin);
                    break;
                }
            }
        }
        return curves;
    }
}
