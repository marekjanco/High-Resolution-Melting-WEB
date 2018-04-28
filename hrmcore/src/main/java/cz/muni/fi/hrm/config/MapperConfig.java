package cz.muni.fi.hrm.config;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public DozerBeanMapper dozerMapper(){
        return new DozerBeanMapper();
    }
}
