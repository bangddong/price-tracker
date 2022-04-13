package com.bd.tracker.api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("com.bd.tracker.core")
@EnableJpaRepositories("com.bd.tracker.core")
@Configuration
public class JpaConfig {
}
