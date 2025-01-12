package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;
import static com.ed.payment.libs.common.exception.ErrorCode.DUPLICATED_ORDER_REQUEST;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.HandleFailPaymentCommand;
import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.PaymentFail;
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

  private final ReadPaymentPort readPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final OrderPaymentResponse<OrderPaymentConfirmResponse> orderPaymentConfirmResponse;

  @Transactional
  @Override
  public PaymentFail handleFailPayment(HandleFailPaymentCommand command) {
    if (DUPLICATED_ORDER_ERROR.equalsIgnoreCase(command.getCode())) {
      throw new CustomException(DUPLICATED_ORDER_REQUEST);
    }

    Payment payment = readPaymentPort.getPaymentByOrderPublicId(command.getOrderId());
    updatePaymentPort.updatePaymentStatusById(payment.getPaymentId(), ABORTED);
    createPaymentHistoryPort.createFailPaymentHistory(payment.getPaymentId(), ABORTED);

    sendOrderPaymentConfirmResponse(command.getOrderId());

    return PaymentFail.of(command.getCode(), command.getMessage(), command.getOrderId());
  }

  private void sendOrderPaymentConfirmResponse(String orderId) {
    orderPaymentConfirmResponse.send(ORDER_PAYMENT_CONFIRM_RESPONSE, OrderPaymentConfirmResponse.newBuilder()
        .setIsSuccess(false)
        .setOrderId(orderId)
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }
}
