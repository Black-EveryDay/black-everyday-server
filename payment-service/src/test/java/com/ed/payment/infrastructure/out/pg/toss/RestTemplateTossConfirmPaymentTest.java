package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
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
    final Long paymentId = 1L;
    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String userPublicId = UUID.randomUUID().toString();
    final PaymentStatus paymentStatus = READY;
    final String orderPublicId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final Long amount = 10000L;
    final LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    final LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    Payment payment = new Payment(
        paymentId, paymentPublicId,
        null, idempotencyKey,
        userPublicId, paymentStatus, orderPublicId, orderName,
        amount, confirmDeadline, cancelDeadLine);

    final String paymentKey = "tgen_20250107154634hYNt7";
    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";
    PaymentDone response = PaymentDone.of(paymentKey, orderPublicId, amount, amount, DONE, lastTransactionKey);

    // stubbing
    when(mockRestTemplateTossConfirmPayment.confirmPayment(payment, paymentKey))
        .thenReturn(response);

    // when
    mockRestTemplateTossConfirmPayment.confirmPayment(payment, paymentKey);

    // then
    verify(mockRestTemplateTossConfirmPayment)
        .confirmPayment(payment, paymentKey);
  }
}