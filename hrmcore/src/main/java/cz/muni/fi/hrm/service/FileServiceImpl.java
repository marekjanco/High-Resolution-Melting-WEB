package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;

@Service
public class FileServiceImpl implements FileService {

    @Inject
    private RefCurveRepository refCurveRepository;

    @Override
    public List<RefCurveDTO> readUploadedFile(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        List<RefCurveDTO> curves = null;
        if (filename.endsWith(".csv")) {
            curves = this.parseDataFromCsv(file);
        } else if (filename.endsWith(".xlsx")) {
            curves = this.parseDataFromXlsx(file);
        } else if (filename.endsWith(".xls")) {
            curves = this.parseDataFromXls(file);
        } else {
            //FIXME vynimka, nejaky error
        }
        return curves;

    }

    @Override
    public Workbook generateFileOfDbData() throws IOException {
        RefCurve temperature = refCurveRepository.findTemperature();
        List<RefCurve> refCurves = refCurveRepository.findAll();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Reference curves");

        for(int i = 0; i < temperature.getValues().size(); ++i){
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(1);
            cell.setCellValue(temperature.getValues().get(i));
        }
        try (FileOutputStream outputStream = new FileOutputStream("data.xlsx")) {
            workbook.write(outputStream);
        }
        return workbook;
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
                    try{
                        parsed = Double.parseDouble(record.get(i));
                        curves.get(i).values.add(parsed);
                    }catch(Exception e){
                        //FIXME vynimka
                    }
                }
            }
        } finally {
            parser.close();
        }
        return curves;
    }

    private List<RefCurveDTO> parseDataFromXlsx(final MultipartFile file) throws IOException {
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        List<RefCurveDTO> ret = this.iterateExcelSheet(iterator);

        wb.close(); //FIXME finally block
        return ret;
    }

    private List<RefCurveDTO> parseDataFromXls(MultipartFile file) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook(file.getInputStream());
        HSSFSheet sheet = wb.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        List<RefCurveDTO> ret = this.iterateExcelSheet(iterator);

        wb.close(); //FIXME finally block
        return ret;
    }

    private List<RefCurveDTO> iterateExcelSheet(Iterator<Row> iterator) {
        boolean firstRow = true;
        List<RefCurveDTO> curves = null;

        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            if (firstRow) {
                curves = this.checkExcelHeader(cellIterator);
                firstRow = false;
            }
            int cellCounter = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
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
        }
        return curves;
    }

    private List<RefCurveDTO> checkCsvHeader(CSVRecord record) {
        List<RefCurveDTO> curves = new ArrayList<>();
        for(int i = 0; i < record.size(); ++i){
            RefCurveDTO curve = new RefCurveDTO();
            Double parsed = null;
            try{
                parsed = Double.parseDouble(record.get(i));
                curve.values.add(parsed);
            }catch(Exception e){
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

}
