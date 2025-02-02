package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.READY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentConfirmResponseEvent;
import com.ed.payment.application.port.in.command.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.validator.PaymentConfirmValidator;
import java.io.IOException;
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
  private OutPortPgMapper outPortPgMapper;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Mock
  private PaymentConfirmValidator confirmValidator;

  @Mock
  private ConfirmPaymentPort confirmPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentConfirmResponseEvent> producer;

  @Test
  @DisplayName("confirmPayment: 결제 승인 정보를 입력 받아 결제 승인을 요청한다.")
  void confirmPayment_success() throws IOException {
    // given
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderPublicId = UUID.randomUUID().toString();
    final Long amount = 10000L;

    ConfirmPaymentCommand confirmRequest = createConfirmRequest(paymentKey, orderPublicId, amount);
    Payment payment = createPayment(paymentKey, orderPublicId, amount);
    PaymentDoneResponse confirmResponse = createConfirmResponse(paymentKey, orderPublicId, amount);

    // stubbing
    when(getPaymentPort.getPaymentByOrderPublicId(any()))
        .thenReturn(payment);

    when(confirmPaymentPort.confirmPayment(any(), any()))
        .thenReturn(confirmResponse);

    doNothing().when(updatePaymentPort)
        .updatePaymentStatusAndPaymentKeyById(any(), any(), any());

    when(producer.send(any(), any()))
        .thenReturn(true);

    // when
    confirmPaymentService.confirmPayment(confirmRequest);

    // then
    verify(getPaymentPort).getPaymentByOrderPublicId(any());
    verify(confirmPaymentPort).confirmPayment(any(), any());
    verify(updatePaymentPort).updatePaymentStatusAndPaymentKeyById(any(), any(), any());
    verify(producer).send(any(), any());
  }

  private ConfirmPaymentCommand createConfirmRequest(String paymentKey, String orderPublicId, Long amount) {
    return ConfirmPaymentCommand.of("NORMAL", paymentKey, orderPublicId, amount);
  }

  private Payment createPayment(String paymentKey, String orderPublicId, Long amount) {
    return Payment.builder()
        .paymentId(1L)
        .paymentPublicId(UUID.randomUUID().toString())
        .paymentKey(paymentKey)
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

  private PaymentDoneResponse createConfirmResponse(String paymentKey, String orderPublicId, Long amount) {
    return PaymentDoneResponse.builder()
        .paymentKey(paymentKey)
        .orderId(orderPublicId)
        .totalAmount(amount)
        .balanceAmount(amount)
        .paymentStatus(DONE)
        .build();
  }
}