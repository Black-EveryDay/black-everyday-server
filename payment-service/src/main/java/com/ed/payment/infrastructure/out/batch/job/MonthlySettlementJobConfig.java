package com.ed.payment.infrastructure.out.batch.job;

import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import com.ed.payment.infrastructure.out.batch.listener.JobDurationListener;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class MonthlySettlementJobConfig extends DefaultBatchConfiguration {

  private static final String JOB_NAME = "monthlySettlementJob";
  private static final String STEP_NAME = "monthlySettlementStep";
  private static final int DEFAULT_CHUNK_SIZE = 3000;

  private final AbstractPagingItemReader<AggregatedDailySettlement> aggregateDailySettlementItemReader;
  private final ItemWriter<AggregatedDailySettlement> monthlySettlementItemWriter;

  @Bean
  public Job monthlySettlementJob(JobRepository jobRepository, Step monthlySettlementStep) {
    return new JobBuilder(JOB_NAME, jobRepository)
        .listener(new JobDurationListener())
        .start(monthlySettlementStep)
        .build();
  }

  @Bean
  public Step monthlySettlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder(STEP_NAME, jobRepository)
        .<AggregatedDailySettlement, AggregatedDailySettlement>chunk(DEFAULT_CHUNK_SIZE, transactionManager)
        .reader(aggregateDailySettlementItemReader)
        .writer(monthlySettlementItemWriter)
        .build();
  }
}