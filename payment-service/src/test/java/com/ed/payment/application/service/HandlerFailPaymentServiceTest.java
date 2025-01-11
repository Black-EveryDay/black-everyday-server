package com.ed.payment.application.service;

import static com.ed.payment.libs.common.exception.ErrorCode.DUPLICATED_ORDER_REQUEST;
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
import com.ed.payment.application.port.in.HandleFailPaymentCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentConfirmProducer;
import com.ed.payment.libs.common.exception.CustomException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HandleRequestPaymentFailControllerTest {

  private HandlerFailPaymentService handlerFailPaymentService;

  @Mock
  private ReadPaymentPort readPaymentPort;

  @Mock
  private UpdatePaymentPort updatePaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Mock
  private OrderPaymentConfirmProducer<OrderPaymentConfirmResponse> producer;

  @BeforeEach
  void setUp() {
    handlerFailPaymentService = new HandlerFailPaymentService(
        readPaymentPort, updatePaymentPort, createPaymentHistoryPort, producer);
  }

  @Test
  @DisplayName("handleFailPayment: 결제 실패 정보를 입력 받아 결제 실패 원인을 반환한다.")
  void handleFailPayment() {
    // given
    final String code = "FAILED_CARD_COMPANY";
    final String message = "카드사 점검 중으로 다른 카드를 이용해 주세요.";
    final String orderId = UUID.randomUUID().toString();
    HandleFailPaymentCommand request = HandleFailPaymentCommand.of(code,
        message, orderId);

    // stubbing
    when(readPaymentPort.findPaymentByOrderPublicId(anyString()))
        .thenReturn(mock(Payment.class));

    doNothing()
        .when(updatePaymentPort)
        .updatePaymentStatusById(anyLong(), any(PaymentStatus.class));

    doNothing()
        .when(createPaymentHistoryPort)
        .createFailPaymentHistory(anyLong(), any((PaymentStatus.class)));

    // when
    handlerFailPaymentService.handleFailPayment(request);

    // then
    verify(readPaymentPort).findPaymentByOrderPublicId(anyString());
    verify(updatePaymentPort).updatePaymentStatusById(anyLong(), any(PaymentStatus.class));
    verify(createPaymentHistoryPort).createFailPaymentHistory(anyLong(), any(PaymentStatus.class));
  }

  @Test
  @DisplayName("handleFailPayment: 결제 실패 정보를 입력 받아 결제 실패 원인을 반환한다.")
  void handleFailPayment_duplicated_order_case() {
    // given
    final String code = "DUPLICATED_ORDER_ID";
    final String message = "이미 승인 및 취소가 진행된 중복된 주문번호 입니다. 다른 주문번호로 진행해주세요.";
    final String orderId = UUID.randomUUID().toString();
    HandleFailPaymentCommand request = HandleFailPaymentCommand.of(code,message, orderId);

    // expected
    assertThatThrownBy(() -> handlerFailPaymentService.handleFailPayment(request))
        .isInstanceOf(CustomException.class)
        .hasMessage(DUPLICATED_ORDER_REQUEST.getMessage());

    verify(readPaymentPort, never()).findPaymentByOrderPublicId(anyString());
    verify(updatePaymentPort, never()).updatePaymentStatusById(anyLong(), any(PaymentStatus.class));
    verify(createPaymentHistoryPort, never()).createFailPaymentHistory(anyLong(), any(PaymentStatus.class));
  }
}