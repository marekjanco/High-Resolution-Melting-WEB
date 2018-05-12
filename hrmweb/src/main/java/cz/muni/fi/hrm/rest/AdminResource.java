package cz.muni.fi.hrm.rest;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.FileService;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/admin")
public class AdminResource {

    static final String GET_ALL = "refCurve/getAll";
    static final String DELETE = "refCurve/delete";
    final static String UPLOAD_AND_SAVE = "uploadExcelAndSave";

    @Inject
    private FileService fileService;

    @Inject
    private RefCurveService refCurveService;

    @RequestMapping(value = "/" + GET_ALL, method = RequestMethod.GET)
    public List<RefCurveDTO> getAll() {
        return refCurveService.getAll();
    }

    @RequestMapping(value = "/" + DELETE, method = RequestMethod.PUT)
    public void delete(@RequestBody RefCurveDTO dto) {
        refCurveService.delete(dto);
    }

    @RequestMapping(value = "/" + UPLOAD_AND_SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    void uploadFileAndSave(@RequestParam(value="file", required=true) MultipartFile file, HttpServletRequest request) throws IOException {
        List<RefCurveDTO> data = fileService.readUploadedFile(file, true);
        refCurveService.addNewDataToDB(data);
    }
}
