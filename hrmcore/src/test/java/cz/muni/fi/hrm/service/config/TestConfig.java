package cz.muni.fi.hrm.service.config;

import cz.muni.fi.hrm.config.MapperConfig;
import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

@Configuration
@ComponentScan("cz.muni.fi.hrm.service")
@Import({
        MapperConfig.class
})
public class TestConfig {

    @Bean
    public RefCurveRepository refCurveRepository(){
        return mock(RefCurveRepository.class);
    }
}
