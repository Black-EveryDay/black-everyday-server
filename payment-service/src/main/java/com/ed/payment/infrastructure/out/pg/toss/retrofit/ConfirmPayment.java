package com.ed.payment.infrastructure.out.pg.toss.retrofit;

import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.ConfirmPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor.IdempotencyKeyInterceptor;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmPayment implements ConfirmPaymentPort {

  private final TossPaymentClient tossPaymentClient;
  private final IdempotencyKeyInterceptor idempotencyKeyInterceptor;
  private final PaymentPgMapper paymentPgMapper;

  @Override
  public PaymentDoneResponse confirmPayment(String idempotencyKey, ConfirmPaymentRequest request) throws IOException {
    try (IdempotencyKeyInterceptor interceptor = idempotencyKeyInterceptor) {
      interceptor.setIdempotencyKey(idempotencyKey);
      return paymentPgMapper.confirmResponseToApplication(
          Objects.requireNonNull(tossPaymentClient.confirmPayment(request)
              .execute()
              .body()));
    }
  }
}
