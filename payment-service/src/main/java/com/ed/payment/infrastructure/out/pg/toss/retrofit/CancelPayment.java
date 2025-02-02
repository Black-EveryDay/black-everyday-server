package com.ed.payment.infrastructure.out.pg.toss.retrofit;

import com.ed.payment.application.port.out.pg.CancelPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor.IdempotencyKeyInterceptor;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelPayment implements CancelPaymentPort {

  private final TossPaymentClient tossPaymentClient;
  private final IdempotencyKeyInterceptor idempotencyKeyInterceptor;
  private final PaymentPgMapper paymentPgMapper;

  @Override
  public PaymentCanceledResponse cancelPayment(String paymentKey,
      String idempotencyKey, CancelPaymentRequest request) throws IOException {
    try (IdempotencyKeyInterceptor interceptor = idempotencyKeyInterceptor) {
      interceptor.setIdempotencyKey(idempotencyKey);
      return paymentPgMapper.cancelResponseToApplication(Objects.requireNonNull(
          tossPaymentClient.cancelPayment(paymentKey, request)
              .execute()
              .body()));
    }
  }
}