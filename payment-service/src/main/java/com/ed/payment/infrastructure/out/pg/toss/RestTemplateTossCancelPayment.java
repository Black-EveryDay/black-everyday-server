package com.ed.payment.infrastructure.out.pg.toss;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ed.payment.application.port.out.pg.PaymentCanceled;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestTemplateTossCancelPayment implements TossCancelPayment {

  private static final String AUTHENTICATION_SCHEME = "Basic ";

  private final PaymentPgMapper paymentPGMapper;

  @Value("${pg.tosspayments.secret-key}")
  private String secretKey;
  @Value("${pg.tosspayments.base-url}")
  private String baseUrl;

  @Override
  public PaymentCanceled cancelPayment(String paymentKey, String idempotencyKey, String cancelReason) {
    RestTemplate restTemplate = new RestTemplate();

    URI url = URI.create(baseUrl + "/" + paymentKey + "/cancel");
    HttpHeaders headers = generateHeaders(idempotencyKey);
    Map<String, Object> body = generateBody(cancelReason);

    HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
    return paymentPGMapper.mapCancelResponseToApplication(
        restTemplate.postForObject(url, httpEntity, TossPaymentCanceled.class));
  }

  private Map<String, Object> generateBody(String cancelReason) {
    Map<String, Object> body = new HashMap<>();
    body.put("cancelReason", cancelReason);
    return body;
  }

  private HttpHeaders generateHeaders(String idempotencyKey) {
    HttpHeaders headers = new HttpHeaders();

    byte[] encodedBytes = Base64.getEncoder()
        .encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
    String authorizations = AUTHENTICATION_SCHEME.concat(new String(encodedBytes));

    headers.add(AUTHORIZATION, authorizations);
    headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    headers.add("Idempotency-Key", idempotencyKey);
    return headers;
  }
}