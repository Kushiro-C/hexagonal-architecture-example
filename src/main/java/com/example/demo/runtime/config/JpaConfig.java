package com.example.demo.runtime.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.example.demo.infrastructure.database.repository")
@EntityScan(basePackages = "com.example.demo.infrastructure.database.entity")
public class JpaConfig {
}
