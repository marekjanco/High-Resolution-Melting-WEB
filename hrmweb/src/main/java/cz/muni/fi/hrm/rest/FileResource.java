package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.FileService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileResource {
    public final static String UPLOAD_EXCEL = "uploadExcel";

    @Inject
    private FileService fileService;

    @RequestMapping(value = "/" + UPLOAD_EXCEL, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<RefCurveDTO> uploadFile(@RequestParam(value="file", required=true) MultipartFile file, HttpServletRequest request) throws IOException {
        return fileService.readUploadedFile(file);
    }

}
