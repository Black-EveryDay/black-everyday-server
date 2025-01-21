package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestTemplateTossConfirmPaymentTest {

  @Mock
  private RestTemplateTossConfirmPayment mockRestTemplateTossConfirmPayment;

  @Test
  @DisplayName("confirmPayment: 결제 정보를 입력 받아 Tosspayments 의 결제 승인 외부 API 를 요청한다.")
  void confirmPayment() {
    // given
    final String orderPublicId = UUID.randomUUID().toString();
    final Long amount = 10000L;
    Payment payment = createPayment(orderPublicId, amount);

    final String paymentKey = "tgen_20250107154634hYNt7";
    PaymentDoneResponse response = PaymentDoneResponse.builder()
        .paymentKey(paymentKey)
        .orderId(orderPublicId)
        .totalAmount(amount)
        .balanceAmount(amount)
        .paymentStatus(DONE)
        .build();

    // stubbing
    when(mockRestTemplateTossConfirmPayment.confirmPayment(payment, paymentKey))
        .thenReturn(response);

    // when
    mockRestTemplateTossConfirmPayment.confirmPayment(payment, paymentKey);

    // then
    verify(mockRestTemplateTossConfirmPayment)
        .confirmPayment(payment, paymentKey);
  }

  private Payment createPayment(String orderPublicId, Long amount) {
    return Payment.builder()
        .paymentId(1L)
        .paymentPublicId(UUID.randomUUID().toString())
        .paymentKey(null)
        .idempotencyKey(UUID.randomUUID().toString())
        .userId(UUID.randomUUID().toString())
        .paymentStatus(READY)
        .orderPublicId(orderPublicId)
        .orderName("피자맛 호빵")
        .totalAmount(amount)
        .balanceAmount(amount)
        .confirmDeadline(LocalDateTime.now().plusDays(5))
        .cancelDeadLine(LocalDateTime.now().plusDays(5))
        .build();
  }
}