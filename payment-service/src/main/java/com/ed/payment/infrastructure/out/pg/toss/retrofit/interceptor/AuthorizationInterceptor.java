package com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import okhttp3.Interceptor;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationInterceptor implements Interceptor {

  private static final String AUTHENTICATION_SCHEME = "Basic ";

  @Value("${pg.tosspayments.secret-key}")
  private String tossSecretKey;

  @Override
  public Response intercept(Chain chain) throws IOException {
    return chain.proceed(chain.request().newBuilder()
        .addHeader(AUTHORIZATION, generateAuthorization())
        .build());
  }

  private String generateAuthorization() {
    byte[] encodedBytes = Base64.getEncoder()
        .encode((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));
    return AUTHENTICATION_SCHEME.concat(new String(encodedBytes));
  }
}
