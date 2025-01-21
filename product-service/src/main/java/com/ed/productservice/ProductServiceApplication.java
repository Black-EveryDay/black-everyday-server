package com.ed.productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;

@SpringBootApplication
public class ProductServiceApplication {

  RedisSentinelConfiguration redisSetinelConfig = new RedisSentinelConfiguration();


  public static void main(String[] args) {
    SpringApplication.run(ProductServiceApplication.class, args);
  }
}
