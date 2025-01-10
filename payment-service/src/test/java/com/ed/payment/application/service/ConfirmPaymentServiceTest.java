package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_AMOUNT_MISMATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentProducer;
import com.ed.payment.infrastructure.out.mq.record.OrderPaymentResponse;
import com.ed.payment.libs.common.exception.CustomException;
import com.ed.payment.libs.common.helper.TransactionHelper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfirmPaymentServiceTest {

  private ConfirmPaymentService confirmPaymentService;

  @Mock
  private ReadPaymentPort readPaymentPort;

  @Mock
  private TransactionHelper transactionHelper;

  @Mock
  private ConfirmPaymentPort confirmPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentProducer<OrderPaymentResponse> producer;

  @BeforeEach
  void setUp() {
    confirmPaymentService = new ConfirmPaymentService(
        transactionHelper, readPaymentPort, updatePaymentPort,
        confirmPaymentPort, createPaymentHistoryPort, producer);
  }

  @Test
  @DisplayName("confirmPayment: 결제 승인 정보를 입력 받아 결제 승인을 요청한다.")
  void confirmPayment_verifying_success() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderId = UUID.randomUUID().toString();
    final Long sameRequestAmount = 10000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderId, sameRequestAmount);

    final Long paymentId = 1L;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    Payment payment = new Payment(paymentId, null, idempotencyKey, orderId, orderName, originAmount);

    final String paymentStatus = DONE.name();
    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";
    PaymentDone response = PaymentDone.of(paymentKey, orderId, originAmount, originAmount, paymentStatus, lastTransactionKey);

    // stubbing
    when(readPaymentPort.findPayment(orderId))
        .thenReturn(payment);

    when(confirmPaymentPort.confirmPayment(payment, request.getPaymentKey()))
        .thenReturn(response);

    when(confirmPaymentPort.isPaymentConfirmed(paymentStatus))
        .thenReturn(true);

    doNothing()
        .when(updatePaymentPort)
        .updatePaymentAfterVerifying(paymentId, getPaymentStatus(paymentStatus), paymentKey);

    doNothing().when(createPaymentHistoryPort)
        .createConfirmSuccessPaymentHistory(paymentId, lastTransactionKey, getPaymentStatus(paymentStatus), originAmount, originAmount);

    when(producer.send(any(String.class), any(OrderPaymentResponse.class)))
        .thenReturn(true);

    // when
    PaymentDone result = confirmPaymentService.confirmPayment(request);

    // then
    assertThat(result).isEqualTo(response);
    verify(readPaymentPort).findPayment(orderId);
    verify(transactionHelper, never()).executeInNewTransaction(any());
    verify(confirmPaymentPort).confirmPayment(payment, paymentKey);
    verify(confirmPaymentPort, times(2)).isPaymentConfirmed(paymentStatus);
    verify(updatePaymentPort).updatePaymentAfterVerifying(paymentId, getPaymentStatus(paymentStatus), paymentKey);
    verify(createPaymentHistoryPort).createConfirmSuccessPaymentHistory(paymentId, lastTransactionKey, getPaymentStatus(paymentStatus), originAmount, originAmount);
    verify(producer).send(any(String.class), any(OrderPaymentResponse.class));
  }

  @Test
  @DisplayName("confirmPayment: 결제 승인 정보를 입력 받아 결제 승인을 요청한다.")
  void confirmPayment_verifying_fail() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderId = UUID.randomUUID().toString();
    final Long differentRequestAmount = 1000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderId, differentRequestAmount);

    final Long paymentId = 1L;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    Payment payment = new Payment(paymentId, null, idempotencyKey, orderId, orderName, originAmount);

    final String paymentStatus = VERIFY_FAILED.name();

    // stubbing
    when(readPaymentPort.findPayment(orderId))
        .thenReturn(payment);

    doCallRealMethod().when(transactionHelper)
        .executeInNewTransaction(any(Runnable.class));

    doNothing().when(updatePaymentPort)
        .updatePaymentAfterVerifying(paymentId, getPaymentStatus(paymentStatus), paymentKey);

    doNothing().when(createPaymentHistoryPort)
        .createFailPaymentHistory(paymentId, getPaymentStatus(paymentStatus));

    // expected
    assertThatThrownBy(() -> confirmPaymentService.confirmPayment(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_AMOUNT_MISMATCH.getMessage());

    verify(readPaymentPort).findPayment(orderId);
    verify(transactionHelper).executeInNewTransaction(any());
    verify(confirmPaymentPort, never()).confirmPayment(payment, paymentKey);
    verify(confirmPaymentPort, never()).isPaymentConfirmed(any());
    verify(updatePaymentPort).updatePaymentAfterVerifying(paymentId, getPaymentStatus(paymentStatus), paymentKey);
    verify(createPaymentHistoryPort).createFailPaymentHistory(paymentId, getPaymentStatus(paymentStatus));
  }

  private PaymentStatus getPaymentStatus(String paymentStatus) {
    return PaymentStatus.valueOf(paymentStatus);
  }
}