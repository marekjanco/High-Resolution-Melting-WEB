package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ErrorMarginDTO;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * service reads and generates excel and csv data that are sent from client
 */
public interface FileService {

    static final Double TEMPERATURE_MAX = 90.0;
    static final Double TEMPERATURE_MIN = 70.0;
    static final Integer HEADER_SIZE = 4;

    /**
     * read and parse excel or csv file
     * @param file excel or csv file to read and parse
     * @param marginErrorSheet true if excel contains margin error sheet, used to update db from admin
     * @return list of readed reference curves
     * @throws IOException
     */
    List<RefCurveDTO> readUploadedFile(MultipartFile file, boolean marginErrorSheet) throws IOException;

    /**
     * generate excel workbook, that contains data sheet and margin errors sheet
     * @return generated excel workbook
     * @throws IOException
     */
    HSSFWorkbook generateFileOfDbData() throws IOException;
}
