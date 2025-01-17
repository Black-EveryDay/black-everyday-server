package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.OrderPaymentCancelResponseEvent;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.CreateCancelPaymentHistoryRequest;
import com.ed.payment.application.port.out.persistence.dtos.UpdateCancelPaymentRequest;
import com.ed.payment.application.port.out.pg.CancelPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.validator.PaymentCancelValidator;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelPaymentServiceTest {

  @InjectMocks
  private CancelPaymentService cancelPaymentService;

  @Mock
  private OutPortPersistenceMapper outPortPersistenceMapper;

  @Mock
  private OutPortPgMapper outPortPgMapper;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Mock
  private PaymentCancelValidator cancelValidator;

  @Mock
  private CancelPaymentPort cancelPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentCancelResponseEvent> producer;

  @Test
  @DisplayName("cancelPayment: 주문 취소 시 발행한 취소 메시지를 기반으로 결제를 취소한다.")
  void cancelPayment_success() {
  	// given
    final String userPublicId = UUID.randomUUID().toString();
    final String paymentPublicId = UUID.randomUUID().toString();
    final Long paymentId = 1L;
    final String paymentKey ="tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long totalAmount = 10000L;
    final Long balanceAmount = 10000L;
    final Long cancelAmount = 10000L;

    OrderPaymentCancelRequestEvent cancelRequest = createCancelRequest(
        userPublicId, orderPublicId, paymentPublicId, cancelAmount);

    Payment payment = createPayment(
        paymentPublicId, paymentKey, userPublicId,
        orderPublicId, totalAmount, balanceAmount);

    PaymentCanceledResponse cancelResponse = createPaymentResponse(
        paymentKey, orderPublicId, totalAmount, balanceAmount, cancelAmount);

    CancelPaymentRequest cancelPaymentRequest = outPortPgMapper.cancelPaymentToPg(payment, cancelRequest);

    UpdateCancelPaymentRequest updateCancelPaymentRequest = outPortPersistenceMapper.updateCancelPaymentToPersistence(
        paymentId, cancelResponse);

    CreateCancelPaymentHistoryRequest cancelPaymentHistoryRequest = outPortPersistenceMapper.cancelHistoryToPersistence(
        paymentId, cancelResponse);

    // stubbing
    when(getPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    when(cancelPaymentPort.cancelPayment(cancelPaymentRequest))
        .thenReturn(cancelResponse);

    doNothing().when(updatePaymentPort)
        .updatePaymentStatusAndIdempotencyKeyById(updateCancelPaymentRequest);

    doNothing().when(createPaymentHistoryPort)
        .createCancelPaymentHistory(cancelPaymentHistoryRequest);

    when(producer.send(anyString(), any()))
        .thenReturn(true);

    // when
    cancelPaymentService.cancelPayment(cancelRequest);

    // then
    verify(getPaymentPort).getPaymentByOrderPublicId(anyString());
    verify(updatePaymentPort).updatePaymentStatusAndIdempotencyKeyById(updateCancelPaymentRequest);
    verify(createPaymentHistoryPort).createCancelPaymentHistory(cancelPaymentHistoryRequest);
    verify(producer).send(anyString(), any());
  }

  private Payment createPayment(
      String paymentPublicId, String paymentKey, String userPublicId,
      String orderPublicId, Long totalAmount, Long balanceAmount) {
    return Payment.builder()
        .paymentId(1L)
        .paymentPublicId(paymentPublicId)
        .paymentKey(paymentKey)
        .idempotencyKey(UUID.randomUUID().toString())
        .userId(userPublicId)
        .paymentStatus(DONE)
        .orderPublicId(orderPublicId)
        .orderName("피자맛 호빵")
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .confirmDeadline(LocalDateTime.now().plusDays(5))
        .cancelDeadLine(LocalDateTime.now().plusDays(5))
        .build();
  }

  private OrderPaymentCancelRequestEvent createCancelRequest(
      String userPublicId, String orderPublicId, String paymentPublicId,
      Long cancelAmount) {
    return OrderPaymentCancelRequestEvent.newBuilder()
        .setUserId(userPublicId)
        .setOrderId(orderPublicId)
        .setPaymentId(paymentPublicId)
        .setCancelAmount(cancelAmount)
        .setCancelReason("단순 변심")
        .setRequestDateTime(LocalDateTime.now())
        .build();
  }

  private PaymentCanceledResponse createPaymentResponse(
      String paymentKey, String orderPublicId, Long totalAmount,
      Long balanceAmount, Long cancelAmount) {
    return PaymentCanceledResponse.builder()
        .paymentKey(paymentKey)
        .orderId(orderPublicId)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .cancelAmount(cancelAmount)
        .paymentStatus(CANCELED)
        .lastTransactionKey("9C62B18EEF0DE3EB7F4422EB6D14BC6E")
        .build();
  }
}
