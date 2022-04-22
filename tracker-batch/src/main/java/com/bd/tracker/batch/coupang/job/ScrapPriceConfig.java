package com.bd.tracker.batch.coupang.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ScrapPriceConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job scrapPriceJob(Step crapPriceStep) {
        return jobBuilderFactory.get("scrapPriceJob")
                .incrementer(new RunIdIncrementer())
                .start(crapPriceStep)
                .build();
    }

    @JobScope
    @Bean
    public Step scrapPriceStep() {
        return stepBuilderFactory.get("scrapPriceStep")
                .tasklet(new ScrapPriceTasklet())
                .build();
    }

}
