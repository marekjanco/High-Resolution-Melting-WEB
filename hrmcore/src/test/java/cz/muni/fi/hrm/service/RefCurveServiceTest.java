package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.ErrorMarginDTO;
import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.entity.ErrorMargin;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.dozer.DozerBeanMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class RefCurveServiceTest {


    @Mock
    private RefCurveRepository refCurveRepository;

    @Mock
    private DozerBeanMapper mapper;

    @InjectMocks
    private final RefCurveService refCurveService = new RefCurveServiceImpl();

    @Before
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreate() {
        RefCurve curve = this.getRefCurve();
        refCurveService.create(curve);
        verify(refCurveRepository, times(1)).saveAndFlush(curve);
    }

    @Test
    public void testCreateOrUpdate() {
        RefCurveDTO dto = this.getRefCurveDTO();
        RefCurve curve = this.getRefCurve();
        Mockito.when(mapper.map(dto, RefCurve.class)).thenReturn(curve);
        refCurveService.addNewDataToDB(Arrays.asList(dto));
        verify(refCurveRepository, times(1)).saveAndFlush(curve);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        refCurveService.create(null);
    }

    @Test(expected = Exception.class)
    public void testCreateWithoutName() {
        RefCurve curve = getRefCurve();
        curve.setName(null);
        refCurveService.create(curve);
    }

    @Test
    public void testDelete() {
        RefCurveDTO dto = this.getRefCurveDTO();
        RefCurve curve = this.getRefCurve();
        Mockito.when(refCurveRepository.findByName(dto.name)).thenReturn(curve);
        refCurveService.delete(dto);
        verify(refCurveRepository, times(1)).delete(curve);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNull() {
        refCurveService.delete(null);
    }

    @Test
    public void testGetAll() {
        RefCurveDTO dto = this.getRefCurveDTO();
        RefCurveDTO dto2 = this.getRefCurveDTO();
        RefCurve curve = this.getRefCurve();
        RefCurve curve2 = this.getRefCurve();
        curve2.setName("xxx");
        curve2.setValues(Arrays.asList(1.0, 1.2, 1.03, 5.8, 4.9));
        dto2.setName("xxx");
        dto2.setValues(Arrays.asList(1.0, 1.2, 1.03, 5.8, 4.9));
        Mockito.when(refCurveRepository.findAll()).thenReturn(Arrays.asList(curve, curve2));
        Mockito.when(mapper.map(curve, RefCurveDTO.class)).thenReturn(dto);
        Mockito.when(mapper.map(curve2, RefCurveDTO.class)).thenReturn(dto2);

        List<RefCurveDTO> list = refCurveService.getAll();
        Assert.assertEquals(list, Arrays.asList(dto, dto2));
    }

    @Test
    public void testGetAllNamesAndAcronyms() {
        RefCurveDTO dto = this.getRefCurveDTO();
        RefCurveDTO dto2 = this.getRefCurveDTO();
        RefCurve curve = this.getRefCurve();
        RefCurve curve2 = this.getRefCurve();
        curve2.setName("xxx");
        curve2.setValues(Arrays.asList(1.0, 1.2, 1.03, 5.8, 4.9));
        dto2.setName("xxx");
        dto2.setValues(Arrays.asList(1.0, 1.2, 1.03, 5.8, 4.9));
        Mockito.when(refCurveRepository.findAll()).thenReturn(Arrays.asList(curve, curve2));
        Mockito.when(mapper.map(curve, RefCurveDTO.class)).thenReturn(dto);
        Mockito.when(mapper.map(curve2, RefCurveDTO.class)).thenReturn(dto2);

        List<RefCurveDTO> list = refCurveService.getNamesAndAcronyms();
        dto.setValues(null);
        dto2.setValues(null);
        Assert.assertEquals(list, Arrays.asList(dto, dto2));
    }

    private RefCurveDTO getRefCurveDTO() {
        ErrorMarginDTO m1 = new ErrorMarginDTO();
        m1.setValues(Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1));
        RefCurveDTO c1 = new RefCurveDTO();
        c1.setName("first");
        c1.setAcronym("f");
        c1.setNote("first test curve");
        c1.setValues(Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0));
        c1.setErrorMargin(m1);
        return c1;
    }

    private RefCurve getRefCurve() {
        ErrorMargin m1 = new ErrorMargin();
        m1.setValues(Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1));
        RefCurve c1 = new RefCurve();
        c1.setName("first");
        c1.setAcronym("f");
        c1.setNote("first test curve");
        c1.setValues(Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0));
        c1.setErrorMargin(m1);
        return c1;
    }
}
