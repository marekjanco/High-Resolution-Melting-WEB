package cz.fi.muni.hrm.repository;

import cz.muni.fi.hrm.repository.RefCurveRepository;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "cz.muni.fi.hrm")
@EntityScan("cz.muni.fi.hrm")
public class TestConfig {
}
