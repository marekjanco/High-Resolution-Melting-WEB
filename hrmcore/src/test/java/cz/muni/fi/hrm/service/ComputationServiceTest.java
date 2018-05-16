package cz.muni.fi.hrm.service;

import cz.muni.fi.hrm.dto.RefCurveDTO;
import cz.muni.fi.hrm.dto.ResultDTO;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class ComputationServiceTest {

    @Mock
    private RefCurveRepository refCurveRepository;


    @Mock
    private DozerBeanMapper mapper;

    @InjectMocks
    private final ComputationService computationService = new ComputationServiceImpl();

    @Before
    public void setupMockito() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAverageCurve() {
        List<RefCurveDTO> list = this.getTwoRefCurvesDTOs();
        RefCurveDTO average = computationService.createAverageCurve(list);

        List<Double> result = Arrays.asList(1.5, 2.5, 3.0, 4.0, 4.5);
        Assert.assertEquals(average.getValues(), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullAverageCurve() {
        RefCurveDTO average = computationService.createAverageCurve(null);
    }

    @Test
    public void testCreateAverageCurveOnEmptyList() {
        RefCurveDTO average = computationService.createAverageCurve(new ArrayList<>());
        Assert.assertEquals(average.getValues().size(), 0);
    }

    @Test
    public void testCreateAverageCurveOnThreeCurves() {
        List<RefCurveDTO> list = this.getThreeRefCurvesDTOs();
        List<Double> result = Arrays.asList(2.0, 3.0, 3.0, 3.33333333333333, 4.0);

        RefCurveDTO average = computationService.createAverageCurve(list);
        Assert.assertTrue(Math.abs(average.getValues().get(3) - result.get(3)) < 0.0000001);
    }

    @Test
    public void testResult() {
        Mockito.when(mapper.map(Mockito.anyObject(), Mockito.anyObject())).thenReturn(
                new RefCurveDTO("first", "f", "first ref curve", 4, Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0))
        );
        Mockito.when(refCurveRepository.findAll()).thenReturn(findAllRefCurves());
        List<Double> values = Arrays.asList(1.2, 1.55, 1.75, 2.15, 2.35, 2.75, 3.05);
        ResultDTO res = computationService.compareDataWithRefCurves(
                Arrays.asList(new RefCurveDTO("", "", "", values)), 95);
        Assert.assertEquals(res.getAverageCurve().getValues(), values);
        Assert.assertEquals(res.getMatched().getValues(), values);
        Assert.assertEquals(res.getNotMatched().getValues(), Arrays.asList(null, null, null, null, null, null, null));
        Assert.assertTrue(res.getNumberOfPoints() == 7);
        Assert.assertTrue(res.getMatchInPerc() == 100.0);
        Assert.assertTrue(res.getPointsFitInMargin() == 7);
    }

    @Test
    public void testResultWithDifferentConfidenceInterval() {
        Mockito.when(refCurveRepository.findAll()).thenReturn(findAllRefCurves());
        Mockito.when(mapper.map(Mockito.anyObject(), Mockito.anyObject())).thenReturn(
                new RefCurveDTO("first", "f", "first ref curve", 4, Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0))
        );
        List<Double> values = Arrays.asList(1.2, 1.55, 1.75, 2.15, 2.35, 2.75, 3.05);
        ResultDTO res = computationService.compareDataWithRefCurves(
                Arrays.asList(new RefCurveDTO("", "", "", null, values)), 98);
        Assert.assertEquals(res.getAverageCurve().getValues(), values);
        Assert.assertEquals(res.getMatched().getValues(), values);
        Assert.assertEquals(res.getNotMatched().getValues(), Arrays.asList(null, null, null, null, null, null, null));
        Assert.assertTrue(res.getNumberOfPoints() == 7);
        Assert.assertTrue(res.getMatchInPerc() == 100.0);
        Assert.assertTrue(res.getPointsFitInMargin() == 7);
    }

    @Test
    public void testResultOnInterval() {
        Mockito.when(mapper.map(Mockito.anyObject(), Mockito.anyObject())).thenReturn(
                new RefCurveDTO("first", "f", "first ref curve", 4, Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0))
        );
        Mockito.when(refCurveRepository.findAll()).thenReturn(findAllRefCurves());
        List<Double> values = Arrays.asList(null, 1.55, 1.25, 2.15, null, null, null);
        ResultDTO res = computationService.compareDataWithRefCurves(
                Arrays.asList(new RefCurveDTO("", "", "", values)), 95);
        Assert.assertTrue(res.getNumberOfPoints() == 3);
        Assert.assertTrue(res.getPointsFitInMargin() == 2);
    }

    @Test
    public void testResultToSecondCurve() {
        Mockito.when(mapper.map(Mockito.anyObject(), Mockito.anyObject())).thenReturn(
                new RefCurveDTO("second", "s", "second ref curve", 4, Arrays.asList(1.2, 1.5, 1.95, 2.1, 2.4, 2.7, 3.3))
        );
        Mockito.when(refCurveRepository.findAll()).thenReturn(findAllRefCurves());
        List<Double> values = Arrays.asList(1.2, 1.5, 1.91, 2.1, 2.4, 2.7, 3.9);
        ResultDTO res = computationService.compareDataWithRefCurves(
                Arrays.asList(new RefCurveDTO("", "", "", values)), 95);
        Assert.assertTrue(res.getNumberOfPoints() == 7);
        Assert.assertTrue(res.getPointsFitInMargin() == 6);
        Assert.assertEquals(res.getMatchedRefCurve().getName(), "second");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testResultOnEmptyData() {
        Mockito.when(refCurveRepository.findAll()).thenReturn(findAllRefCurves());
        List<Double> values = Arrays.asList();
        ResultDTO res = computationService.compareDataWithRefCurves(
                Arrays.asList(new RefCurveDTO("", "", "", values)), 95);
    }

    private List<RefCurveDTO> getTwoRefCurvesDTOs() {
        RefCurveDTO c1 = new RefCurveDTO("first", "f", "first test curve", null);
        c1.setValues(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0));
        RefCurveDTO c2 = new RefCurveDTO("second", "s", "second test curve", null);
        c2.setValues(Arrays.asList(2.0, 3.0, 3.0, 4.0, 4.0));
        List<RefCurveDTO> curves = new ArrayList<>();
        curves.add(c1);
        curves.add(c2);
        return curves;
    }

    private List<RefCurveDTO> getThreeRefCurvesDTOs() {
        List<RefCurveDTO> curves = this.getTwoRefCurvesDTOs();
        RefCurveDTO c3 = new RefCurveDTO("third", "t", "third test curve", null);
        c3.setValues(Arrays.asList(3.0, 4.0, 3.0, 2.0, 3.0));
        curves.add(c3);
        return curves;
    }

    private List<RefCurve> findAllRefCurves() {
        List<RefCurve> curves = new ArrayList<>();
        RefCurve c1 = findFirstCurve();
        RefCurve c2 = findSecondCurve();
        curves.add(c1);
        curves.add(c2);
        return curves;
    }

    private RefCurve findFirstCurve(){
        ErrorMargin m1 = new ErrorMargin();
        m1.setValues(Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1));
        RefCurve c1 = new RefCurve();
        c1.setName("first");
        c1.setAcronym("f");
        c1.setNumberOfSamples(4);
        c1.setNote("first ref curve");
        c1.setValues(Arrays.asList(1.2, 1.5, 1.8, 2.1, 2.4, 2.7, 3.0));
        c1.setErrorMargin(m1);
        return c1;
    }

    private RefCurve findSecondCurve(){
        ErrorMargin m1 = new ErrorMargin();
        m1.setValues(Arrays.asList(0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1));
        RefCurve c2 = new RefCurve();
        c2.setName("second");
        c2.setAcronym("s");
        c2.setNumberOfSamples(4);
        c2.setNote("second ref curve");
        c2.setValues(Arrays.asList(1.2, 1.5, 1.95, 2.1, 2.4, 2.7, 3.3));
        c2.setErrorMargin(m1);
        return c2;
    }



}
