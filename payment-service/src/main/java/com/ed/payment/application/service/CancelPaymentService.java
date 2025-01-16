package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.isCancelSuccess;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CANCEL_RESPONSE;

import com.ed.OrderPaymentCancelRequest;
import com.ed.OrderPaymentCancelResponse;
import com.ed.payment.application.port.in.CancelPaymentUseCase;
import com.ed.payment.application.port.out.mq.Producer;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.CancelPaymentPort;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import com.ed.payment.domain.Payment;
import com.ed.payment.libs.common.validator.PaymentCancelValidator;
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
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final Producer<OrderPaymentCancelResponse> orderPaymentCancelProducer;

  @Transactional
  @Override
  public void cancelPayment(OrderPaymentCancelRequest request) {
    Payment payment = getPaymentPort.getPaymentByOrderPublicId(request.getOrderId());

    payment.validatePayment(cancelValidator, request.getRequestDateTime(), request.getCancelAmount());

    PaymentCanceledResponse paymentCanceledResponse = cancelPaymentPort.cancelPayment(outPortPgMapper.cancelPaymentToPg(payment, request));

    updatePaymentPort.updatePaymentStatusAndIdempotencyKeyById(
        outPortPersistenceMapper.updateCancelPaymentToPersistence(payment.getPaymentId(), paymentCanceledResponse));

    createPaymentHistoryPort.createCancelPaymentHistory(
        outPortPersistenceMapper.cancelHistoryToPersistence(payment.getPaymentId(), paymentCanceledResponse));

    sendOrderPaymentCancelResponse(paymentCanceledResponse, payment.getPaymentPublicId());
  }

  private void sendOrderPaymentCancelResponse(
      PaymentCanceledResponse paymentCanceledResponse, String paymentPublicId) {
    orderPaymentCancelProducer.send(ORDER_PAYMENT_CANCEL_RESPONSE,
        OrderPaymentCancelResponse.newBuilder()
            .setIsSuccess(isCancelSuccess(paymentCanceledResponse.getPaymentStatus()))
            .setOrderId(paymentCanceledResponse.getOrderId())
            .setPaymentId(paymentPublicId)
            .setCancelAmount(paymentCanceledResponse.getCancelAmount())
            .setBalanceAmount(paymentCanceledResponse.getBalanceAmount())
            .setMessageTimestamp(LocalDateTime.now())
            .build());
  }
}
