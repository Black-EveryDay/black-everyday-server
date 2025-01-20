package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.isCanceled;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CANCEL_RESPONSE;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.OrderPaymentCancelResponseEvent;
import com.ed.payment.application.port.in.CancelPaymentUseCase;
import com.ed.payment.application.port.out.mq.Producer;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.CancelPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.libs.common.validator.PaymentCancelValidator;
import com.ed.payment.libs.common.validator.dtos.PaymentValidatorRequest;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentService implements CancelPaymentUseCase {

  private final OutPortPersistenceMapper outPortPersistenceMapper;
  private final OutPortPgMapper outPortPgMapper;
  private final GetPaymentPort getPaymentPort;
  private final PaymentCancelValidator cancelValidator;
  private final CancelPaymentPort cancelPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final Producer<OrderPaymentCancelResponseEvent> orderPaymentCancelProducer;

  @Transactional
  @Override
  public void cancelPayment(OrderPaymentCancelRequestEvent request) {
    Payment payment = getPaymentPort.getPaymentByOrderPublicId(request.getOrderId());

    payment.validatePayment(cancelValidator, createPaymentValidatorRequest(payment, request));

    PaymentCanceledResponse paymentCanceledResponse = cancelPaymentPort.cancelPayment(outPortPgMapper.cancelPaymentToPg(payment, request));

    updatePaymentPort.updatePaymentStatusAndIdempotencyKeyById(
        outPortPersistenceMapper.updateCancelPaymentToPersistence(payment.getPaymentId(), paymentCanceledResponse));

    sendOrderPaymentCancelResponse(paymentCanceledResponse, payment.getPaymentPublicId());
  }

  private PaymentValidatorRequest createPaymentValidatorRequest(
      Payment payment, OrderPaymentCancelRequestEvent request) {
    return PaymentValidatorRequest.of(
        payment, request.getRequestDateTime(), request.getCancelAmount());
  }

  private void sendOrderPaymentCancelResponse(
      PaymentCanceledResponse response, String paymentPublicId) {
    orderPaymentCancelProducer.send(ORDER_PAYMENT_CANCEL_RESPONSE, createPaymentCancelMessage(response, paymentPublicId));
  }

  private OrderPaymentCancelResponseEvent createPaymentCancelMessage(
      PaymentCanceledResponse response, String paymentPublicId) {
    return OrderPaymentCancelResponseEvent.newBuilder()
        .setIsSuccess(isCanceled(response.getPaymentStatus()))
        .setOrderId(response.getOrderId())
        .setPaymentId(paymentPublicId)
        .setCancelAmount(response.getCancelAmount())
        .setBalanceAmount(response.getBalanceAmount())
        .setMessageTimestamp(LocalDateTime.now())
        .build();
  }
}
