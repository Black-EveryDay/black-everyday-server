package com.ed.payment.application.service;

import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_CONFIRM_NOT_ALLOWED;

import com.ed.OrderPaymentConfirmResponseEvent;
import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import com.ed.payment.application.port.in.command.PaymentRequestFailCommand;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.dtos.PaymentFailResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HandleFailPaymentService implements HandleFailPaymentUseCase {

  private static final String DUPLICATED_ORDER_ERROR = "DUPLICATED_ORDER_ID";

  private final GetPaymentPort getPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final OrderPaymentResponse<OrderPaymentConfirmResponseEvent> orderPaymentConfirmResponse;

  @Transactional
  @Override
  public PaymentFailResponse handleFailPayment(PaymentRequestFailCommand command) {
    if (DUPLICATED_ORDER_ERROR.equalsIgnoreCase(command.getCode())) {
      throw new CustomException(PAYMENT_CONFIRM_NOT_ALLOWED);
    }

    Payment payment = getPaymentPort.getPaymentByOrderPublicId(command.getOrderId());
    updatePaymentPort.updatePaymentStatusAbortedById(payment.getPaymentId());

    sendOrderPaymentRequestFailResponse(command.getOrderId());

    return PaymentFailResponse.of(command.getCode(), command.getMessage(), command.getOrderId());
  }

  private void sendOrderPaymentRequestFailResponse(String orderId) {
    orderPaymentConfirmResponse.send(ORDER_PAYMENT_CONFIRM_RESPONSE, createFailMessage(orderId));
  }

  private OrderPaymentConfirmResponseEvent createFailMessage(String orderId) {
    return OrderPaymentConfirmResponseEvent.newBuilder()
        .setIsSuccess(false)
        .setOrderId(orderId)
        .setMessageTimestamp(LocalDateTime.now())
        .build();
  }
}
