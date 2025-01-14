package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.isConfirmSuccess;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentResponse;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

  private final GetPaymentPort getPaymentPort;
  private final ConfirmPaymentPort confirmPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final OrderPaymentResponse<OrderPaymentConfirmResponse> orderPaymentConfirmProducer;

  @Transactional
  @Override
  public PaymentDone confirmPayment(ConfirmPaymentCommand command) {
    Payment payment = getPaymentPort.getPaymentByOrderPublicId(command.getOrderId());

    payment.validateConfirmablePayment(command.getRequestDateTime(), command.getAmount());

    PaymentDone paymentDone = confirmPaymentPort.confirmPayment(payment, command.getPaymentKey());

    updatePaymentPort.updatePaymentStatusAndPaymentKeyById(
        payment.getPaymentId(), paymentDone.getPaymentStatus(), command.getPaymentKey());

    createPaymentHistoryPort.createConfirmSuccessPaymentHistory(
        payment.getPaymentId(), paymentDone.getLastTransactionKey(), paymentDone.getPaymentStatus(),
        paymentDone.getTotalAmount(), paymentDone.getBalanceAmount());

    sendOrderPaymentConfirmResponse(paymentDone, payment.getPaymentPublicId());

    return paymentDone;
  }

  private void sendOrderPaymentConfirmResponse(PaymentDone paymentDone, String paymentPublicId) {
    orderPaymentConfirmProducer.send(ORDER_PAYMENT_CONFIRM_RESPONSE, OrderPaymentConfirmResponse.newBuilder()
        .setIsSuccess(isConfirmSuccess(paymentDone.getPaymentStatus()))
        .setOrderId(paymentDone.getOrderId())
        .setPaymentId(paymentPublicId)
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }
}
