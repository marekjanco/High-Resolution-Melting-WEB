package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ErrorMarginDTO;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    @Override
    public List<RefCurveDTO> readUploadedFile(MultipartFile file, boolean marginErrorSheet) throws IOException {
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
        if(curves == null || curves.get(0) == null){
            throw new IllegalArgumentException("Reading of file was not successful or file is empty");
        }
        if (this.checkIfCurveIsTemperature(curves.get(0))) {
            curves.get(0).setName("temperature");
            curves = this.checkIntervalOfData(curves);
            curves.get(0).setErrorMargin(new ErrorMarginDTO());
        }
        return curves;
    }

    @Override
    public HSSFWorkbook generateFileOfDbData() throws IOException {
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
        return workbook;
    }

    private List<RefCurveDTO> checkIntervalOfData(List<RefCurveDTO> curves) {
        RefCurveDTO temperature = curves.get(0);
        int index = this.indexOfSubinterval(temperature);
        if (index != -1) {
            for (RefCurveDTO curve : curves) {
                List<Double> newValues = new ArrayList<>();
                for (int j = 0; j < index; ++j) {
                    newValues.add(null);
                }
                newValues.addAll(curve.getValues());
                for (int j = temperature.getValues().size(); j < curves.get(0).getValues().size(); ++j) {
                    newValues.add(null);
                }
                curve.setValues(newValues);
            }
        } else {
            this.interpolateData(curves);
        }
        return curves;
    }

    private void interpolateData(List<RefCurveDTO> curves) {
       /* RefCurve dbTemperature = refCurveRepository.findTemperature();
        List<RefCurveDTO> newCurves = new ArrayList<>();
        RefCurveDTO newCurve = new RefCurveDTO();
        int temperatureIndex = 0;
        for(int i = 0; i < dbTemperature.getValues().size(); ++i){
            if(dbTemperature.getValues().get(i) <)
        }*/
    }

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
                        //FIXME vynimka
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
            //FIXME vynimka
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
            //FIXME vynimka
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
                            //FIXME vynimka
                            break;
                    }
                    cellCounter++;
                }
                ++i;
            }
        } catch (IOException e) {
            //FIXME vynimka
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
                if(cellCounter >= curves.size()){
                    throw new IllegalArgumentException("Format violation - there is more cells in row "+i);
                }
                switch (cell.getCellTypeEnum()) {
                    case NUMERIC:
                        curves.get(cellCounter).values.add(cell.getNumericCellValue());
                        break;
                    case STRING:
                    case BOOLEAN:
                        //FIXME vynimka
                        break;
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
                    //FIXME vynimka
                    break;
            }
            ret.add(curve);
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
                        //FIXME vynimka
                        break;
                }
                if(index == 0){
                    ret.add(curve);
                }
                ++i;
            }
            index++;
        }

        return ret;

    }

    private List<RefCurveDTO> connectMarginsToCurves(List<RefCurveDTO> curves, List<ErrorMarginDTO> errorMargins) {
        for(RefCurveDTO curve: curves){
            String name = curve.getName();
            for(ErrorMarginDTO margin: errorMargins){
                if(name.equals(margin.name)){
                    curve.setErrorMargin(margin);
                    break;
                }
            }
        }
        return curves;
    }
}
