package cz.muni.fi.hrm.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void readUploadedFile(MultipartFile file) throws IOException;
}
