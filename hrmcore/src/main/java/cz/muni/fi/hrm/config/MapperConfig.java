package cz.muni.fi.hrm.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configure dozer mapper
 */
@Configuration
public class MapperConfig {
    @Bean
    public DozerBeanMapper dozerMapper(){
        return new DozerBeanMapper();
    }
}
