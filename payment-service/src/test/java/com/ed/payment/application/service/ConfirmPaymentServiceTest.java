package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static com.ed.payment.domain.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.libs.common.exception.ErrorCode.DUPLICATED_ORDER_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_AMOUNT_MISMATCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.exception.CustomException;
import com.ed.payment.libs.common.helper.TransactionHelper;
import java.time.LocalDateTime;
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
  private TransactionHelper transactionHelper;

  @Mock
  private ConfirmPaymentPort confirmPaymentPort;

  @Mock
  private ReadPaymentPort readPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentConfirmResponse> producer;

  @BeforeEach
  void setUp() {
    confirmPaymentService = new ConfirmPaymentService(
        transactionHelper, confirmPaymentPort, readPaymentPort,
        updatePaymentPort, createPaymentHistoryPort, producer);
  }

  @Test
  @DisplayName("confirmPayment: 결제 승인 정보를 입력 받아 결제 승인을 요청한다.")
  void confirmPayment_success() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long sameRequestAmount = 10000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderPublicId, sameRequestAmount);

    final Long paymentId = 1L;
    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String userPublicId = UUID.randomUUID().toString();
    final PaymentStatus paymentStatus = READY;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    final LocalDateTime confirmDeadline = request.getRequestDateTime().plusDays(5);
    final LocalDateTime cancelDeadLine = request.getRequestDateTime().plusDays(5);
    Payment payment = new Payment(
        paymentId, paymentPublicId,
        paymentKey, idempotencyKey,
        userPublicId, paymentStatus, orderPublicId, orderName,
        originAmount, confirmDeadline, cancelDeadLine);

    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";
    PaymentDone response = PaymentDone.of(paymentKey, orderPublicId, originAmount, originAmount, paymentStatus, lastTransactionKey);

    // stubbing
    when(readPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    when(confirmPaymentPort.confirmPayment(payment, paymentKey))
        .thenReturn(response);

    doNothing()
        .when(updatePaymentPort)
        .updatePaymentStatusAndPaymentKeyById(paymentId, paymentStatus, paymentKey);

    doNothing().when(createPaymentHistoryPort)
        .createConfirmSuccessPaymentHistory(paymentId, lastTransactionKey, paymentStatus, originAmount, originAmount);

    when(producer.send(anyString(), any(OrderPaymentConfirmResponse.class)))
        .thenReturn(true);

    // when
    PaymentDone result = confirmPaymentService.confirmPayment(request);

    // then
    assertThat(result).isEqualTo(response);
    verify(readPaymentPort).getPaymentByOrderPublicId(orderPublicId);
    verify(transactionHelper, never()).executeInNewTransaction(any());
    verify(confirmPaymentPort).confirmPayment(payment, paymentKey);
    verify(updatePaymentPort).updatePaymentStatusAndPaymentKeyById(paymentId, paymentStatus, paymentKey);
    verify(createPaymentHistoryPort).createConfirmSuccessPaymentHistory(paymentId, lastTransactionKey, paymentStatus, originAmount, originAmount);
    verify(producer).send(anyString(), any(OrderPaymentConfirmResponse.class));
  }

  @Test
  @DisplayName("confirmPayment: 이미 승인/취소된 결제에 대한 요청 시 실패한다.")
  void confirmPayment_fail_processed_payment() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long sameRequestAmount = 10000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderPublicId, sameRequestAmount);

    final Long paymentId = 1L;
    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String userPublicId = UUID.randomUUID().toString();
    final PaymentStatus processedPaymentStatus = DONE;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    final LocalDateTime expiredConfirmDeadline = request.getRequestDateTime().plusDays(5);
    final LocalDateTime cancelDeadLine = request.getRequestDateTime().plusDays(5);
    Payment payment = new Payment(
        paymentId, paymentPublicId,
        paymentKey, idempotencyKey,
        userPublicId, processedPaymentStatus, orderPublicId, orderName,
        originAmount, expiredConfirmDeadline, cancelDeadLine);

    // stubbing
    when(readPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    // expected
    assertThatThrownBy(() -> confirmPaymentService.confirmPayment(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(DUPLICATED_ORDER_REQUEST.getMessage());

    verify(readPaymentPort).getPaymentByOrderPublicId(orderPublicId);
    verify(transactionHelper, never()).executeInNewTransaction(any());
    verify(confirmPaymentPort, never()).confirmPayment(any(), any());
    verify(updatePaymentPort, never()).updatePaymentStatusAndPaymentKeyById(any(), any(), any());
    verify(createPaymentHistoryPort, never()).createFailPaymentHistory(any(), any());
  }

  @Test
  @DisplayName("confirmPayment: 이미 결제 기한이 만료된 경우 실패한다.")
  void confirmPayment_fail_expired_request() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long sameRequestAmount = 10000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderPublicId, sameRequestAmount);

    final Long paymentId = 1L;
    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String userPublicId = UUID.randomUUID().toString();
    final PaymentStatus paymentStatus = READY;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    final LocalDateTime confirmDeadline = request.getRequestDateTime().minusDays(1);
    final LocalDateTime cancelDeadLine = request.getRequestDateTime().plusDays(5);
    Payment payment = new Payment(
        paymentId, paymentPublicId,
        paymentKey, idempotencyKey,
        userPublicId, paymentStatus, orderPublicId, orderName,
        originAmount, confirmDeadline, cancelDeadLine);

    // stubbing
    when(readPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    // expected
    assertThatThrownBy(() -> confirmPaymentService.confirmPayment(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(EXPIRED_PAYMENT_CONFIRM_REQUEST.getMessage());

    verify(readPaymentPort).getPaymentByOrderPublicId(orderPublicId);
    verify(transactionHelper, never()).executeInNewTransaction(any());
    verify(confirmPaymentPort, never()).confirmPayment(any(), any());
    verify(updatePaymentPort, never()).updatePaymentStatusAndPaymentKeyById(any(), any(), any());
    verify(createPaymentHistoryPort, never()).createFailPaymentHistory(any(), any());
  }

  @Test
  @DisplayName("confirmPayment: 요청 금액이 일치하지 않은 경우 실패한다.")
  void confirmPayment_fail_mismatch_amount() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long differentRequestAmount = 20000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType, paymentKey, orderPublicId, differentRequestAmount);

    final Long paymentId = 1L;
    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String userPublicId = UUID.randomUUID().toString();
    final PaymentStatus paymentStatus = VERIFY_FAILED;
    final String orderName = "피자맛 호빵";
    final Long originAmount = 10000L;
    final LocalDateTime confirmDeadline = request.getRequestDateTime().plusDays(5);
    final LocalDateTime cancelDeadLine = request.getRequestDateTime().plusDays(5);
    Payment payment = new Payment(
        paymentId, paymentPublicId,
        paymentKey, idempotencyKey,
        userPublicId, paymentStatus, orderPublicId, orderName,
        originAmount, confirmDeadline, cancelDeadLine);


    // stubbing
    when(readPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    doCallRealMethod().when(transactionHelper)
        .executeInNewTransaction(any(Runnable.class));

    doNothing().when(updatePaymentPort)
        .updatePaymentStatusAndPaymentKeyById(paymentId, paymentStatus, paymentKey);

    doNothing().when(createPaymentHistoryPort)
        .createFailPaymentHistory(paymentId, paymentStatus);

    // expected
    assertThatThrownBy(() -> confirmPaymentService.confirmPayment(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_AMOUNT_MISMATCH.getMessage());

    verify(readPaymentPort).getPaymentByOrderPublicId(orderPublicId);
    verify(transactionHelper).executeInNewTransaction(any(Runnable.class));
    verify(updatePaymentPort).updatePaymentStatusAndPaymentKeyById(paymentId, paymentStatus, paymentKey);
    verify(createPaymentHistoryPort).createFailPaymentHistory(paymentId, paymentStatus);
    verify(confirmPaymentPort, never()).confirmPayment(payment, paymentKey);
  }
}