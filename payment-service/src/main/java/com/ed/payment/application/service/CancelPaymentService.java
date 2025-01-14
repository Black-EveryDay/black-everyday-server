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
import com.ed.payment.application.port.out.pg.PaymentCanceled;
import com.ed.payment.domain.Payment;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentService implements CancelPaymentUseCase {

  private final GetPaymentPort getPaymentPort;
  private final CancelPaymentPort cancelPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final Producer<OrderPaymentCancelResponse> orderPaymentCancelProducer;

  @Transactional
  @Override
  public void cancelPayment(OrderPaymentCancelRequest request) {
    Payment payment = getPaymentPort.getPaymentByOrderPublicId(request.getOrderId());

    payment.validateCancelablePayment(request.getRequestDateTime(), request.getCancelAmount());

    PaymentCanceled paymentCanceled = cancelPaymentPort.cancelPayment(
        payment.getPaymentKey(), payment.getIdempotencyKey(), request.getCancelReason(), request.getCancelAmount());

    updatePaymentPort.updatePaymentStatusAndIdempotencyKeyById(
        payment.getPaymentId(), paymentCanceled.getPaymentStatus(),
        paymentCanceled.getTotalAmount(), paymentCanceled.getBalanceAmount());

    createPaymentHistoryPort.createCancelSuccessPaymentHistory(
        payment.getPaymentId(), paymentCanceled.getLastTransactionKey(), paymentCanceled.getPaymentStatus(),
        paymentCanceled.getTotalAmount(), paymentCanceled.getBalanceAmount(), paymentCanceled.getCancelAmount(),
        paymentCanceled.getCancelReason());

    sendOrderPaymentCancelResponse(paymentCanceled, payment.getPaymentPublicId());
  }

  private void sendOrderPaymentCancelResponse(PaymentCanceled paymentCanceled, String paymentPublicId) {
    orderPaymentCancelProducer.send(ORDER_PAYMENT_CANCEL_RESPONSE, OrderPaymentCancelResponse.newBuilder()
        .setIsSuccess(isCancelSuccess(paymentCanceled.getPaymentStatus()))
        .setOrderId(paymentCanceled.getOrderId())
        .setPaymentId(paymentPublicId)
        .setCancelAmount(paymentCanceled.getCancelAmount())
        .setBalanceAmount(paymentCanceled.getBalanceAmount())
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }
}
