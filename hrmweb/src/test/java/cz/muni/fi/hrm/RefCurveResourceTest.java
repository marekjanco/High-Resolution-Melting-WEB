package cz.muni.fi.hrm;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.service.RefCurveService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RefCurveResourceTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RefCurveService refCurveService;

    @Test
    public void testGetAllNames() throws Exception {
        RefCurveDTO dto = new RefCurveDTO("test_name", "n", null, null);
        given(refCurveService.getNamesAndAcronyms()).willReturn(Arrays.asList(dto));

        mvc.perform(get("/refCurve/getAllNames")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test_name")));
    }

    @Test
    public void testFindByName() throws Exception {
        RefCurveDTO dto = new RefCurveDTO("test_name", "n", null, Arrays.asList(1.0, 2.0));
        given(refCurveService.findByName(dto.name)).willReturn(dto);

        mvc.perform(get("/refCurve/findByName")
                .param("name", dto.name)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test_name")))
                .andExpect(content().string(containsString("1.0,2.0")));
    }


    @Test
    public void testgetTemperature() throws Exception {
        RefCurveDTO dto = new RefCurveDTO("temperature", "t", null, Arrays.asList(1.0, 2.0));
        given(refCurveService.getTemperature()).willReturn(dto);

        mvc.perform(get("/refCurve/getTemperature")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("temperature")))
                .andExpect(content().string(containsString("1.0,2.0")));
    }
}
