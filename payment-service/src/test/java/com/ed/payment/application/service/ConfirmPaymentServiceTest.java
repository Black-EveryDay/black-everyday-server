package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.READY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ConfirmPaymentServiceTest {

  @InjectMocks
  private ConfirmPaymentService confirmPaymentService;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Mock
  private ConfirmPaymentPort confirmPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentConfirmResponse> producer;

  @Test
  @DisplayName("confirmPayment: 결제 승인 정보를 입력 받아 결제 승인을 요청한다.")
  void confirmPayment_success() {
    // given
    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long sameRequestAmount = 10000L;
    ConfirmPaymentCommand request = ConfirmPaymentCommand.of(paymentType,
        paymentKey, orderPublicId, sameRequestAmount);

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
        originAmount, originAmount, confirmDeadline, cancelDeadLine);

    final String lastTransactionKey = "9C62B18EEF0DE3EB7F4422EB6D14BC6E";
    PaymentDone response = PaymentDone.of(paymentKey, orderPublicId,
        originAmount, originAmount, paymentStatus, lastTransactionKey);

    // stubbing
    when(getPaymentPort.getPaymentByOrderPublicId(orderPublicId))
        .thenReturn(payment);

    when(confirmPaymentPort.confirmPayment(payment, paymentKey))
        .thenReturn(response);

    doNothing()
        .when(updatePaymentPort)
        .updatePaymentStatusAndPaymentKeyById(paymentId, paymentStatus,
            paymentKey);

    doNothing().when(createPaymentHistoryPort)
        .createConfirmSuccessPaymentHistory(paymentId, lastTransactionKey,
            paymentStatus, originAmount, originAmount);

    when(producer.send(anyString(), any(OrderPaymentConfirmResponse.class)))
        .thenReturn(true);

    // when
    PaymentDone result = confirmPaymentService.confirmPayment(request);

    // then
    assertThat(result).isEqualTo(response);
    verify(getPaymentPort).getPaymentByOrderPublicId(orderPublicId);
    verify(confirmPaymentPort).confirmPayment(payment, paymentKey);
    verify(updatePaymentPort).updatePaymentStatusAndPaymentKeyById(paymentId,
        paymentStatus, paymentKey);
    verify(createPaymentHistoryPort).createConfirmSuccessPaymentHistory(
        paymentId, lastTransactionKey, paymentStatus, originAmount,
        originAmount);
    verify(producer).send(anyString(), any(OrderPaymentConfirmResponse.class));
  }
}