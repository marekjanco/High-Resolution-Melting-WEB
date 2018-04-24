package cz.muni.fi.hrm.dbconfig;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableJpaRepositories("cz.muni.fi.hrm")
@EntityScan("cz.muni.fi.hrm")
public class DatabaseConfiguration {}
