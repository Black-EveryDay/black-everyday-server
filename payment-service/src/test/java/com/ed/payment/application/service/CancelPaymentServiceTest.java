package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.READY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentCancelRequest;
import com.ed.OrderPaymentCancelResponse;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.CancelPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentCanceled;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelPaymentServiceTest {

  private CancelPaymentService cancelPaymentService;

  @Mock
  private CancelPaymentPort cancelPaymentPort;

  @Mock
  private ReadPaymentPort readPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentCancelResponse> producer;

  @BeforeEach
  void setUp() {
    cancelPaymentService = new CancelPaymentService(
        cancelPaymentPort, readPaymentPort, updatePaymentPort,
        createPaymentHistoryPort, producer);
  }

  @Test
  @DisplayName("cancelPayment: 주문 취소 시 발행한 취소 메시지를 기반으로 결제를 취소한다.")
  void cancelPayment_success() {
  	// given
    final String userPublicId = UUID.randomUUID().toString();
    final String paymentPublicId = UUID.randomUUID().toString();
    final String cancelReason = "단순 변심";
    final LocalDateTime requestDateTime = LocalDateTime.now();
    final Long paymentId = 1L;
    final String paymentKey ="tgen_20250107154634hYNt7";
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderPublicId = UUID.randomUUID().toString();
    final Long totalAmount = 10000L;
    final Long balanceAmount = 0L;
    final Long cancelAmount = 10000L;
    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";

    OrderPaymentCancelRequest request = OrderPaymentCancelRequest.newBuilder()
        .setUserId(userPublicId)
        .setPaymentId(paymentPublicId)
        .setCancelReason(cancelReason)
        .setRequestDateTime(requestDateTime)
        .build();
    Payment payment = getPayment(request, paymentId, paymentKey, idempotencyKey, paymentPublicId, userPublicId, orderPublicId, cancelReason);
    PaymentCanceled paymentCanceled = getPaymentCanceled(paymentKey, orderPublicId, totalAmount, balanceAmount, cancelAmount, lastTransactionKey);

    // stubbing
    when(readPaymentPort.getCancelablePayment(paymentPublicId, requestDateTime))
        .thenReturn(payment);

    when(cancelPaymentPort.cancelPayment(paymentKey, idempotencyKey, cancelReason))
        .thenReturn(paymentCanceled);

    doNothing().when(updatePaymentPort)
        .updatePaymentStatusById(paymentId, CANCELED);

    doNothing().when(createPaymentHistoryPort)
        .createCancelSuccessPaymentHistory(paymentId, lastTransactionKey,
            CANCELED,totalAmount, balanceAmount, cancelAmount, cancelReason);

    when(producer.send(anyString(), any()))
        .thenReturn(true);

    // when
    cancelPaymentService.cancelPayment(request);

    // then
    verify(readPaymentPort).getCancelablePayment(anyString(), any());
    verify(updatePaymentPort).updatePaymentStatusById(anyLong(), any());
    verify(createPaymentHistoryPort).createCancelSuccessPaymentHistory(anyLong(), anyString(), any(), anyLong(), anyLong(), anyLong(), anyString());
    verify(producer).send(anyString(), any());
  }

  private Payment getPayment(
      OrderPaymentCancelRequest request, Long paymentId, String paymentKey, String idempotencyKey,
      String paymentPublicId, String userPublicId, String orderPublicId, String cancelReason) {
    return new Payment(
       paymentId, paymentPublicId, paymentKey, idempotencyKey,
        userPublicId, READY, orderPublicId, cancelReason, 10000L,
        request.getRequestDateTime().plusDays(5), request.getRequestDateTime().plusDays(5));
  }

  private PaymentCanceled getPaymentCanceled(
      String paymentKey, String orderPublicId,
      Long totalAmount, Long balanceAmount, Long cancelAmount, String lastTransactionKey) {
    return PaymentCanceled.of(
        paymentKey, orderPublicId, totalAmount, balanceAmount, cancelAmount,
        "단순 변심", CANCELED, lastTransactionKey);
  }
}
