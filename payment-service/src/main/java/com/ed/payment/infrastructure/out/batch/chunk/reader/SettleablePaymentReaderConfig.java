package com.ed.payment.infrastructure.out.batch.chunk.reader;

import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SettleablePaymentReaderConfig {

  private static final int DEFAULT_PAGE_SIZE = 1000;

  @Bean
  @JobScope
  public AbstractPagingItemReader<SettleablePaymentResponse> settleablePaymentItemReader(
      EntityManagerFactory emf, @Value("#{jobParameters['requestDateTime']}") LocalDateTime requestDateTime,
      GetPaymentPort getPaymentPort) {
    return new SettleablePaymentCustomItemReader(emf, getPaymentPort, requestDateTime, DEFAULT_PAGE_SIZE);
  }
}
