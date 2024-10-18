package com.dlitv.search.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.dlitv.search.repository")
@EntityScan(value = "com.dlitv.evm.entity")
public class JpaConfiguration {
}
