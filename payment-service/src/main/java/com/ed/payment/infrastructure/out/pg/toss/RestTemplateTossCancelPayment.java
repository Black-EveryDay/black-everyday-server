package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.libs.common.constant.HttpHeaders.IDEMPOTENCY_KEY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentCanceledResponse;
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

  private final PaymentPgMapper paymentPgMapper;

  @Value("${pg.tosspayments.secret-key}")
  private String secretKey;
  @Value("${pg.tosspayments.base-url}")
  private String baseUrl;

  @Override
  public PaymentCanceledResponse cancelPayment(CancelPaymentRequest request) {
    RestTemplate restTemplate = new RestTemplate();

    URI url = URI.create(baseUrl + "/" + request.getPaymentKey() + "/cancel");
    HttpHeaders headers = generateHeaders(request.getIdempotencyKey());
    Map<String, Object> body = generateBody(request.getCancelReason(), request.getCancelAmount());

    HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(body, headers);
    return paymentPgMapper.cancelResponseToApplication(
        restTemplate.postForObject(url, httpEntity, TossPaymentCanceledResponse.class));
  }

  private Map<String, Object> generateBody(String cancelReason, Long cancelAmount) {
    Map<String, Object> body = new HashMap<>();
    body.put("cancelReason", cancelReason);
    body.put("cancelAmount", cancelAmount);
    return body;
  }

  private HttpHeaders generateHeaders(String idempotencyKey) {
    HttpHeaders headers = new HttpHeaders();

    byte[] encodedBytes = Base64.getEncoder()
        .encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
    String authorizations = AUTHENTICATION_SCHEME.concat(new String(encodedBytes));

    headers.add(AUTHORIZATION, authorizations);
    headers.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    headers.add(IDEMPOTENCY_KEY, idempotencyKey);
    return headers;
  }
}