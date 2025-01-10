package com.ed.payment.infrastructure.out.pg.toss;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
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

  private final RestTemplateTossConfirmPayment restTemplateTossConfirmPayment
      = new RestTemplateTossConfirmPayment();

  @Test
  @DisplayName("confirmPayment: 결제 정보를 입력 받아 Tosspayments 의 결제 승인 외부 API 를 요청한다.")
  void confirmPayment() {
    // given
    final Long paymentId = 1L;
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final Long amount = 10000L;
    Payment paymentBeforeVerifying = new Payment(paymentId, null, idempotencyKey, orderId, orderName, amount);

    final String paymentKey = "tgen_20250107154634hYNt7";
    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";
    PaymentDone response = PaymentDone.of(paymentKey, orderId, amount, amount, DONE.name(), lastTransactionKey);

    // stubbing
    when(mockRestTemplateTossConfirmPayment.confirmPayment(paymentBeforeVerifying, paymentKey))
        .thenReturn(response);

    // when
    mockRestTemplateTossConfirmPayment.confirmPayment(paymentBeforeVerifying, paymentKey);

    // then
    verify(mockRestTemplateTossConfirmPayment)
        .confirmPayment(paymentBeforeVerifying, paymentKey);
  }

  @Test
  @DisplayName("isPaymentConfirmed: 입력 받은 결제 상태를 기준으로 결제 승인 여부를 확인한다.")
  void isPaymentConfirmed_true() {
  	// given
    final String paymentStatus = DONE.name();

  	// when
    boolean response = restTemplateTossConfirmPayment.isPaymentConfirmed(paymentStatus);

    // then
    assertThat(response).isTrue();
  }

  @Test
  @DisplayName("isPaymentConfirmed: 입력 받은 결제 상태를 기준으로 결제 승인 여부를 확인한다.")
  void isPaymentConfirmed_false() {
    // given
    final String paymentStatus = ABORTED.name();

    // when
    boolean response = restTemplateTossConfirmPayment.isPaymentConfirmed(paymentStatus);

    // then
    assertThat(response).isFalse();
  }
}