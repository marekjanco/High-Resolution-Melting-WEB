package cz.muni.fi.hrm;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Configuration
@ComponentScan(basePackageClasses = {SampleDataServiceImpl.class})
public class LoadSampleDataConfig {

    @Inject
    private SampleDataService sampleDataService;

    @PostConstruct
    public void loadData() {
        sampleDataService.loadData();
    }
}
