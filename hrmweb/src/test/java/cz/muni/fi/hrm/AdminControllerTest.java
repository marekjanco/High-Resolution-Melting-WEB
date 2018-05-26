package cz.muni.fi.hrm;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.FileService;
import cz.muni.fi.hrm.service.RefCurveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RefCurveService refCurveService;

    @MockBean
    private FileService fileService;

    @Test
    public void testForbiddenResponse() throws Exception {
        mvc.perform(get("/admin/refCurve/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        mvc.perform(get("/admin/uploadExcelAndSave")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
        mvc.perform(get("/admin/refCurve/delete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    public void testGetAll() throws Exception {
        RefCurveDTO dto = new RefCurveDTO("test_name", "n", null, null);
        given(refCurveService.getAll()).willReturn(Arrays.asList(dto));

        mvc.perform(get("/admin/refCurve/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test_name")));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    public void testDelete() throws Exception {
        mvc.perform(get("/admin/refCurve/delete")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    public void testUpload() throws Exception {
        mvc.perform(get("/admin/uploadExcelAndSave")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

