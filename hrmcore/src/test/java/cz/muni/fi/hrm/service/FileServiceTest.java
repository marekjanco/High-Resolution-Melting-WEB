package cz.muni.fi.hrm.service;

import com.sun.org.apache.xpath.internal.operations.Mult;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.Arrays;
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

    @Test
    public void testReadHeaderOfExcelFile() throws Exception {
        MultipartFile file = returnMultipartFile("header.xlsx");
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.get(0).getName(), "first");
        Assert.assertEquals(curves.get(1).getName(), "second");
        Assert.assertEquals(curves.get(2).getName(), "third");
    }

    @Test
    public void testReadValuesOfExcelFile() throws Exception {
        MultipartFile file = returnMultipartFile("values.xlsx");
        Mockito.when(refCurveRepository.findTemperature()).thenReturn(getTemperature());
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.size(), 3);
        Assert.assertEquals(curves.get(0).getValues(), Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        Assert.assertEquals(curves.get(1).getValues(), Arrays.asList(1.1, 2.1, 3.1, 4.1, 5.1));
        Assert.assertEquals(curves.get(2).getValues(), Arrays.asList(0.8, 1.5, 2.2, 2.9, 3.6));
    }

    @Test
    public void testReadValuesOfCsvFile() throws Exception {
        MultipartFile file = returnMultipartFile("values.csv");
        Mockito.when(refCurveRepository.findTemperature()).thenReturn(getTemperature());
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.size(), 3);
        Assert.assertEquals(curves.get(0).getValues(), Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        Assert.assertEquals(curves.get(1).getValues(), Arrays.asList(1.1, 2.1, 3.1, 4.1, 5.1));
        Assert.assertEquals(curves.get(2).getValues(), Arrays.asList(0.8, 1.5, 2.2, 2.9, 3.6));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testReadOneCurveLongerThanOthers() throws Exception {
        MultipartFile file = returnMultipartFile("one_collumn_bigger.xlsx");
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);
    }

    @Test
    public void testDataOnInterval() throws Exception {
        Mockito.when(refCurveRepository.findTemperature()).thenReturn(getTemperature());
        MultipartFile file = returnMultipartFile("not_full_temperature_interval.xlsx");
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.size(), 2);
        Assert.assertEquals(curves.get(0).getValues(), Arrays.asList(null, 1.0, 1.0, 1.0, null));
        Assert.assertEquals(curves.get(1).getValues(), Arrays.asList(null, 1.0, 1.0, 1.0, null));
    }

    @Test
    public void testDataOnIntervalBiggerThanTemperature() throws Exception {
        Mockito.when(refCurveRepository.findTemperature()).thenReturn(getTemperature());
        MultipartFile file = returnMultipartFile("bigger_than_temperature_interval.xlsx");
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.size(), 2);
        Assert.assertEquals(curves.get(0).getValues(), Arrays.asList(2.0, 3.0, 4.0, 5.0, 6.0));
        Assert.assertEquals(curves.get(1).getValues(), Arrays.asList(12.0, 13.0, 14.0, 15.0, 16.0));
    }

    @Test
    public void testInterpolate() throws Exception {
        Mockito.when(refCurveRepository.findTemperature()).thenReturn(getTemperature());
        MultipartFile file = returnMultipartFile("interpolate.xlsx");
        List<RefCurveDTO> curves = fileService.readUploadedFile(file, false);

        Assert.assertEquals(curves.size(), 2);
        Assert.assertEquals(curves.get(0).getValues(), Arrays.asList(null, 1.0, 2.0, 3.0, null));
        Assert.assertEquals(curves.get(1).getValues(), Arrays.asList(null, 12.5, 13.5, 14.5, null));

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

    private RefCurve getTemperature() {
        RefCurve t = new RefCurve();
        t.setName("temperature");
        t.setAcronym("t");
        t.setNote("test temperature");
        t.setValues(Arrays.asList(75.0, 75.5, 76.0, 76.5, 77.0));
        return t;
    }
}
