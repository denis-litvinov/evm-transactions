package com.dlitv.storage.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(value = "com.dlitv.storage.repository")
@EntityScan(value = "com.dlitv.evm.entity")
public class JpaConfiguration {
}
