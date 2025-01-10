package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateTossConfirmPayment implements TossConfirmPayment {

  private static final String AUTHENTICATION_SCHEME = "Basic ";

  @Value("${pg.tosspayments.secret-key}")
  private String secretKey;
  @Value("${pg.tosspayments.base-url}")
  private String baseUrl;

  @Override
  public PaymentDone confirmPayment(Payment payment, String paymentKey) {
    RestTemplate restTemplate = new RestTemplate();

    URI url = URI.create(baseUrl + "/confirm");
    HttpHeaders headers = generateHeaders(payment.getIdempotencyKey());
    Map<String, Object> body = generateBody(paymentKey, payment.getOrderId(), payment.getAmount());

    HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
    return restTemplate.postForObject(url, httpEntity, PaymentDone.class);
  }

  @Override
  public boolean isPaymentConfirmed(String paymentStatus) {
    return DONE.name().equalsIgnoreCase(paymentStatus);
  }

  private Map<String, Object> generateBody(
      String paymentKey, String orderId, Long amount) {
    Map<String, Object> body = new HashMap<>();
    body.put("orderId", orderId);
    body.put("amount", amount);
    body.put("paymentKey", paymentKey);
    return body;
  }

  private HttpHeaders generateHeaders(String idempotencyKey) {
    HttpHeaders headers = new HttpHeaders();

    byte[] encodedBytes = Base64.getEncoder()
        .encode((secretKey + ":")
            .getBytes(StandardCharsets.UTF_8));
    String authorizations = AUTHENTICATION_SCHEME.concat(new String(encodedBytes));

    headers.add(AUTHORIZATION, authorizations);
    headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    headers.add("Idempotency-Key", idempotencyKey);
    return headers;
  }
}