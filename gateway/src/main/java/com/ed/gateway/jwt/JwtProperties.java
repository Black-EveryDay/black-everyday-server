package com.ed.gateway.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties("service.jwt")
public class JwtProperties {

  private final String secretKey;

  @ConstructorBinding
  public JwtProperties(String secretKey) {
    this.secretKey = secretKey;
  }
}