package com.ed.payment.infrastructure.out.batch.job;

import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
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
public class DailySettlementJobConfig extends DefaultBatchConfiguration {

  private static final String JOB_NAME = "dailySettlementJob";
  private static final String STEP_NAME = "dailySettlementStep";
  private static final int DEFAULT_CHUNK_SIZE = 1000;

  private final AbstractPagingItemReader<SettleablePaymentResponse> settleablePaymentItemReader;
  private final ItemWriter<SettleablePaymentResponse> dailySettlementItemWriter;

  @Bean
  public Job dailySettlementJob(JobRepository jobRepository, Step dailySettlementStep) {
    return new JobBuilder(JOB_NAME, jobRepository)
        .listener(new JobDurationListener())
        .start(dailySettlementStep)
        .build();
  }

  @Bean
  public Step dailySettlementStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    return new StepBuilder(STEP_NAME, jobRepository)
        .<SettleablePaymentResponse, SettleablePaymentResponse>chunk(DEFAULT_CHUNK_SIZE, transactionManager)
        .reader(settleablePaymentItemReader)
        .writer(dailySettlementItemWriter)
        .build();
  }
}