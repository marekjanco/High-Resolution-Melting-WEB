package cz.muni.fi.hrm.service;

import com.sun.org.apache.xpath.internal.operations.Mult;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static junit.framework.TestCase.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class FileServiceTest {

    @Mock
    private RefCurveRepository refCurveRepository;

    @InjectMocks
    private final FileService fileService = new FileServiceImpl();

    @Test(expected = IllegalArgumentException.class)
    public void testUnknownFileType() throws IOException {
        MultipartFile file = new MockMultipartFile("word.docx",
                "word.docx", "text/plain", new byte[]{});
        fileService.readUploadedFile(file, false);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadEmptyFile() throws Exception {
        MultipartFile file = returnMultipartFile("empty.xlsx");
        fileService.readUploadedFile(file, false);
    }

    private MultipartFile returnMultipartFile(String name) throws URISyntaxException {
        Path path = Paths.get(getClass().getResource("/" + name).toURI());
        String contentType = "text/plain";
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            fail();
        }
        MultipartFile result = new MockMultipartFile(name,
                name, contentType, content);
        return result;
    }
}
