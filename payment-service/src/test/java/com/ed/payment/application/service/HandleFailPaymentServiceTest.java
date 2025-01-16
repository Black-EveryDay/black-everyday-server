package com.ed.payment.application.service;

import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.command.PaymentRequestFailCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.exception.CustomException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HandleFailPaymentServiceTest {

  @InjectMocks
  private HandleFailPaymentService handleFailPaymentService;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentResponse<OrderPaymentConfirmResponse> producer;

  @Test
  @DisplayName("handleFailPayment: 결제 실패 정보를 입력 받아 결제 실패 원인을 반환한다.")
  void handleFailPayment() {
    // given
    final String code = "FAILED_CARD_COMPANY";
    PaymentRequestFailCommand command = createFailPaymentCommand(code);

    // stubbing
    when(getPaymentPort.getPaymentByOrderPublicId(anyString()))
        .thenReturn(mock(Payment.class));

    doNothing()
        .when(updatePaymentPort)
        .updatePaymentStatusById(anyLong(), any(PaymentStatus.class));

    doNothing()
        .when(createPaymentHistoryPort)
        .createFailPaymentHistory(anyLong());

    when(producer.send(anyString(), any(OrderPaymentConfirmResponse.class)))
        .thenReturn(true);

    // when
    handleFailPaymentService.handleFailPayment(command);

    // then
    verify(getPaymentPort).getPaymentByOrderPublicId(anyString());
    verify(updatePaymentPort).updatePaymentStatusById(anyLong(), any(PaymentStatus.class));
    verify(createPaymentHistoryPort).createFailPaymentHistory(anyLong());
    verify(producer).send(anyString(), any(OrderPaymentConfirmResponse.class));
  }

  @Test
  @DisplayName("handleFailPayment: 결제 실패 정보 중 이미 승인/취소된 결제의 경우 예외를 던진다.")
  void handleFailPayment_duplicated_order_case() {
    // given
    final String code = "DUPLICATED_ORDER_ID";
    PaymentRequestFailCommand command = createFailPaymentCommand(code);

    // expected
    assertThatThrownBy(
        () -> handleFailPaymentService.handleFailPayment(command))
        .isInstanceOf(CustomException.class)
        .hasMessage(PAYMENT_CONFIRM_NOT_ALLOWED.getMessage());

    verify(getPaymentPort, never()).getPaymentByOrderPublicId(anyString());
    verify(updatePaymentPort, never()).updatePaymentStatusById(anyLong(), any(PaymentStatus.class));
    verify(createPaymentHistoryPort, never()).createFailPaymentHistory(anyLong());
  }

  private PaymentRequestFailCommand createFailPaymentCommand(String code) {
    return PaymentRequestFailCommand.builder()
        .code(code)
        .message("카드사 점검 중으로 다른 카드를 이용해 주세요.")
        .orderId(UUID.randomUUID().toString())
        .build();
  }
}