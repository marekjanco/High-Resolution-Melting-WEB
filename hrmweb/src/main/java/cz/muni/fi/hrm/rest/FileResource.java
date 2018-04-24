package cz.muni.fi.hrm.rest;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@RestController
@RequestMapping("/file")
public class FileResource {
    public final static String UPLOAD_EXCEL = "uploadExcel";

    @RequestMapping(value = "/" + UPLOAD_EXCEL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadFile(@RequestParam(value="file", required=true) MultipartFile file, HttpServletRequest request) throws IOException {
        if(file.getOriginalFilename().contains(".csv")){
            final Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
            //CSVParser parser = CSVFormat.EXCEL.parse(new InputStreamReader(file.getInputStream(), Charset.forName("UTF-8")));
            CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL);
            try {
                for (CSVRecord record : parser) {
                    System.out.println(record.get(0));
                    //System.out.println(record.get(1));
                }
            } finally {
                parser.close();
            }
        }else{
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            System.out.println(sheet.getRow(0));
        }
    }

}
