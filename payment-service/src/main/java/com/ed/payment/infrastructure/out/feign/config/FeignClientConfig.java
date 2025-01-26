package com.ed.payment.infrastructure.out.feign.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

  @Bean
  public ErrorDecoder errorDecoder() {
    return new FeignErrorDecoder();
  }
}
