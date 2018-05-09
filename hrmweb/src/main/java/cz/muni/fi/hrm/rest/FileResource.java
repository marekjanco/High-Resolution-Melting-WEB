package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.FileService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileResource {
    final static String UPLOAD_EXCEL = "uploadExcel";
    final static String GENERATE_DB_DATA = "generateFileDbData";

    @Inject
    private FileService fileService;

    private static Logger logger = LoggerFactory.getLogger(FileResource.class);
    
    @RequestMapping(value = "/" + UPLOAD_EXCEL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<RefCurveDTO> uploadFile(@RequestParam(value="file", required=true) MultipartFile file, HttpServletRequest request) throws IOException {
        return fileService.readUploadedFile(file, false);
    }

    /**
     * generates .xls file of data that are saved in db
     * @param response to the response will be set content of this xls file
     */
    @RequestMapping(value = "/" + GENERATE_DB_DATA, method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
    public void generateDbData(HttpServletResponse response) throws IOException {
        HSSFWorkbook wb = fileService.generateFileOfDbData();

        String filename = "data.xls";
        response.setContentType("application/xls");
        response.setHeader("Content-disposition", "attachment; filename=" + filename);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            wb.write(outputStream);
            outputStream.flush();
        }
        catch (Exception e) {
            logger.error("Unable to write excel data to the output stream", e);
            throw new IllegalArgumentException("Unable to generate data from db");
        }finally{
            wb.close();
        }
    }

}
