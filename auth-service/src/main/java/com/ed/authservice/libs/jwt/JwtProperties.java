package com.ed.authservice.libs.jwt;

import java.time.Duration;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties("jwt")
public class JwtProperties {

  private final String issuer;
  private final String secretKey;
  private final Duration duration;

  @ConstructorBinding
  public JwtProperties(String issuer, String secretKey, Long duration) {
    this.issuer = issuer;
    this.secretKey = secretKey;
    this.duration = Duration.ofSeconds(duration);
  }
}