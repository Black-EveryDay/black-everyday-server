package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.pg.PaymentCanceled;
import com.ed.payment.domain.PaymentStatus;
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

    final String orderId = UUID.randomUUID().toString();
    final Long totalAmount = 10000L;
    final Long balanceAmount = 0L;
    final Long cancelAmount = 10000L;
    final PaymentStatus paymentStatus = CANCELED;
    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";

    PaymentCanceled response = PaymentCanceled.of(
        paymentKey, orderId, totalAmount, balanceAmount, cancelAmount,
        cancelReason, paymentStatus, lastTransactionKey);

    // stubbing
    when(mockRestTemplateTossCancelPayment.cancelPayment(paymentKey, idempotencyKey, cancelReason, cancelAmount))
        .thenReturn(response);

    // when
    mockRestTemplateTossCancelPayment.cancelPayment(paymentKey, idempotencyKey, cancelReason, cancelAmount);

    // then
    verify(mockRestTemplateTossCancelPayment).cancelPayment(paymentKey, idempotencyKey, cancelReason, cancelAmount);
  }
}