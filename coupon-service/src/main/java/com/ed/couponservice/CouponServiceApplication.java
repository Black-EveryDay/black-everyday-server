package com.ed.couponservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CouponServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CouponServiceApplication.class, args);
  }

}
