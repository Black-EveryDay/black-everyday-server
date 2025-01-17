package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.isConfirmed;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;

import com.ed.OrderPaymentConfirmResponseEvent;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.in.command.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import com.ed.payment.libs.common.validator.PaymentConfirmValidator;
import com.ed.payment.libs.common.validator.dtos.PaymentValidatorRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

  private final OutPortPersistenceMapper outPortPersistenceMapper;
  private final GetPaymentPort getPaymentPort;
  private final PaymentConfirmValidator confirmValidator;
  private final ConfirmPaymentPort confirmPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final OrderPaymentResponse<OrderPaymentConfirmResponseEvent> orderPaymentConfirmProducer;

  @Transactional
  @Override
  public PaymentDoneResponse confirmPayment(ConfirmPaymentCommand command) {
    Payment payment = getPaymentPort.getPaymentByOrderPublicId(command.getOrderId());

    payment.validatePayment(confirmValidator, createPaymentValidatorRequest(payment, command));

    PaymentDoneResponse paymentDoneResponse = confirmPaymentPort.confirmPayment(payment, command.getPaymentKey());

    updatePaymentPort.updatePaymentStatusAndPaymentKeyById(
        payment.getPaymentId(), paymentDoneResponse.getPaymentStatus(), command.getPaymentKey());

    createPaymentHistoryPort.createConfirmPaymentHistory(
        outPortPersistenceMapper.confirmHistoryToPersistence(payment.getPaymentId(), paymentDoneResponse));

    sendOrderPaymentConfirmResponse(paymentDoneResponse, payment.getPaymentPublicId());

    return paymentDoneResponse;
  }

  private PaymentValidatorRequest createPaymentValidatorRequest(
      Payment payment, ConfirmPaymentCommand command) {
    return PaymentValidatorRequest.of(
        payment, command.getRequestDateTime(), command.getAmount());
  }

  private void sendOrderPaymentConfirmResponse(
      PaymentDoneResponse response, String paymentPublicId) {
    orderPaymentConfirmProducer.send(ORDER_PAYMENT_CONFIRM_RESPONSE, createPaymentConfirmMessage(response, paymentPublicId));
  }

  private OrderPaymentConfirmResponseEvent createPaymentConfirmMessage(
      PaymentDoneResponse response, String paymentPublicId) {
    return OrderPaymentConfirmResponseEvent.newBuilder()
        .setIsSuccess(isConfirmed(response.getPaymentStatus()))
        .setOrderId(response.getOrderId())
        .setPaymentId(paymentPublicId)
        .setMessageTimestamp(LocalDateTime.now())
        .build();
  }
}
