package com.ed.payment.infrastructure.out.pg.toss.retrofit.config;

import static java.util.concurrent.TimeUnit.SECONDS;

import com.ed.payment.infrastructure.out.pg.toss.retrofit.TossPaymentClient;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor.AuthorizationInterceptor;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor.IdempotencyKeyInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
@RequiredArgsConstructor
public class RetrofitConfig {

  private final AuthorizationInterceptor authorizationInterceptor;
  private final IdempotencyKeyInterceptor idempotencyKeyInterceptor;

  @Value("${pg.tosspayments.base-url}")
  private String tossBaseUrl;

  @Bean
  public TossPaymentClient tossPaymentClient(Retrofit retrofit) {
    return retrofit.create(TossPaymentClient.class);
  }

  @Bean
  public Retrofit retrofit(OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(tossBaseUrl)
        .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
        .client(okHttpClient)
        .build();
  }

  @Bean
  public OkHttpClient okHttpClient() {
    return new OkHttpClient.Builder()
        .connectTimeout(5, SECONDS)
        .writeTimeout(15, SECONDS)
        .readTimeout(15, SECONDS)
        .addInterceptor(authorizationInterceptor)
        .addInterceptor(idempotencyKeyInterceptor)
        .build();
  }
}
