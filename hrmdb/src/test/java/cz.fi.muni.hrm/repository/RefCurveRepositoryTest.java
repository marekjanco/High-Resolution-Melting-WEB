package cz.fi.muni.hrm.repository;

import cz.muni.fi.hrm.entity.ErrorMargin;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Ref;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {TestConfig.class})
public class RefCurveRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RefCurveRepository refCurveRepository;

    private RefCurve curve;
    private ErrorMargin margin;

    @Before
    public void setup(){
        ErrorMargin margin = new ErrorMargin();
        margin.setValues(Arrays.asList(1.0, 1.0, 1.0, 1.0, 1.0, 1.0));
        RefCurve curve = new RefCurve();
        curve.setName("Trichenalla xxx");
        curve.setAcronym("txx");
        curve.setNote("this is test trichinella");
        curve.setValues(Arrays.asList(0.0, 1.0, 2.0, 3.0, 5.0, 6.0));
        curve.setErrorMargin(margin);
        this.curve = curve;
        this.margin = margin;
    }

    @Test
    public void createTest() throws Exception {
        refCurveRepository.saveAndFlush(curve);
        List<RefCurve> list = refCurveRepository.findAll();
        Assert.assertTrue(list.size() == 1);
    }

    @Test
    public void findByNameTest() throws Exception {
        refCurveRepository.saveAndFlush(curve);
        RefCurve curveFromDB = refCurveRepository.findByName(curve.getName());
        this.compareCurves(curve, curveFromDB);
    }

    @Test
    public void deleteTest(){
        refCurveRepository.saveAndFlush(curve);
        refCurveRepository.delete(curve.getId());

        List<RefCurve> list = refCurveRepository.findAll();
        Assert.assertTrue(list.size() == 0);
    }

    @Test
    public void updateTest(){
        refCurveRepository.saveAndFlush(curve);
        RefCurve c = refCurveRepository.getOne(curve.getId());
        c.setName("newName");
        refCurveRepository.saveAndFlush(c);
        c = refCurveRepository.getOne(curve.getId());
        Assert.assertEquals("newName",c.getName());
    }

    @Test(expected = Exception.class)
    public void createNullAcronym(){
        curve.setAcronym(null);
        refCurveRepository.saveAndFlush(curve);
    }

    @Test(expected = Exception.class)
    public void createNotUniqueName(){
        curve.setName("name1");
        refCurveRepository.saveAndFlush(curve);
        RefCurve c2 = new RefCurve();
        c2.setName("name1");
        c2.setValues(Arrays.asList(0.0, 0.1));
        c2.setErrorMargin(new ErrorMargin());
        refCurveRepository.saveAndFlush(c2);
    }

    @Test(expected = Exception.class)
    public void createWithNullValues(){
        curve.setValues(null);
        refCurveRepository.saveAndFlush(curve);
    }

    private void compareCurves(RefCurve r1, RefCurve r2){
        Assert.assertEquals(r1.getName(), r2.getName());
        Assert.assertEquals(r1.getAcronym(), r2.getAcronym());
        Assert.assertEquals(r1.getNote(), r2.getNote());
        Assert.assertEquals(r1.getValues(), r2.getValues());
        Assert.assertEquals(r1.getErrorMargin(), r2.getErrorMargin());
    }
}