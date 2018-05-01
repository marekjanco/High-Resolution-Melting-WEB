package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ErrorMarginDTO;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    static final Double TEMPERATURE_MAX = 90.0;
    static final Double TEMPERATURE_MIN = 70.0;

    List<RefCurveDTO> readUploadedFile(MultipartFile file, boolean marginErrorSheet) throws IOException;
    HSSFWorkbook generateFileOfDbData() throws IOException;
}
