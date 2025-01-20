package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestTemplateTossCancelPaymentTest {

  @Mock
  private RestTemplateTossCancelPayment mockRestTemplateTossCancelPayment;

  @Test
  @DisplayName("cancelPayment: 결제 취소 정보를 입력 받아 Tosspayments 의 결제 취소 외부 API 를 요청한다.")
  void cancelPayment() {
    // given
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String idempotencyKey = UUID.randomUUID().toString();
    final String cancelReason = "단순 변심";
    final Long cancelAmount = 10000L;

    CancelPaymentRequest request = createRequest(
        paymentKey, idempotencyKey, cancelReason, cancelAmount);

    PaymentCanceledResponse response = createResponse(
        paymentKey, cancelAmount, cancelReason);

    // stubbing
    when(mockRestTemplateTossCancelPayment.cancelPayment(request))
        .thenReturn(response);

    // when
    mockRestTemplateTossCancelPayment.cancelPayment(request);

    // then
    verify(mockRestTemplateTossCancelPayment).cancelPayment(request);
  }

  private CancelPaymentRequest createRequest(
      String paymentKey, String idempotencyKey,
      String cancelReason, Long cancelAmount) {
    return CancelPaymentRequest.builder()
        .paymentKey(paymentKey)
        .idempotencyKey(idempotencyKey)
        .cancelReason(cancelReason)
        .cancelAmount(cancelAmount)
        .build();
  }

  private PaymentCanceledResponse createResponse(
      String paymentKey, Long cancelAmount, String cancelReason) {
    return PaymentCanceledResponse.builder()
        .paymentKey(paymentKey)
        .orderId(UUID.randomUUID().toString())
        .totalAmount(10000L)
        .balanceAmount(0L)
        .cancelAmount(cancelAmount)
        .cancelReason(cancelReason)
        .paymentStatus(CANCELED)
        .build();
  }
}