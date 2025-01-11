package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.domain.PaymentStatus.isSuccess;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_AMOUNT_MISMATCH;

import com.ed.OrderPaymentConfirmResponse;
import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.OrderPaymentConfirmProducer;
import com.ed.payment.libs.common.exception.CustomException;
import com.ed.payment.libs.common.helper.TransactionHelper;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

  private final TransactionHelper transactionHelper;
  private final ReadPaymentPort readPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final ConfirmPaymentPort confirmPaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;
  private final OrderPaymentConfirmProducer<OrderPaymentConfirmResponse> producer;

  @Transactional
  @Override
  public PaymentDone confirmPayment(ConfirmPaymentCommand command) {
    Payment payment = readPaymentPort.findPaymentByOrderPublicId(command.getOrderId());

    validateRequest(payment, command);

    PaymentDone paymentDone = confirmPaymentPort.confirmPayment(payment, command.getPaymentKey());

    updatePaymentPort.updatePaymentStatusAndPaymentKeyById(
        payment.getPaymentId(), paymentDone.getPaymentStatus(), command.getPaymentKey());

    createPaymentHistoryPort.createConfirmSuccessPaymentHistory(
        payment.getPaymentId(), paymentDone.getLastTransactionKey(), paymentDone.getPaymentStatus(),
        paymentDone.getTotalAmount(), paymentDone.getBalanceAmount());

    sendOrderPaymentConfirmResponse(paymentDone);

    return paymentDone;
  }

  private void validateRequest(Payment payment, ConfirmPaymentCommand command) {
    if (payment.isNotValidAmount(command.getAmount())) {
      transactionHelper.executeInNewTransaction(() -> {
        updatePaymentPort.updatePaymentStatusAndPaymentKeyById(payment.getPaymentId(), VERIFY_FAILED, command.getPaymentKey());
        createPaymentHistoryPort.createFailPaymentHistory(payment.getPaymentId(), VERIFY_FAILED);
      });

      throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
    }
  }

  private void sendOrderPaymentConfirmResponse(PaymentDone paymentDone) {
    producer.send(ORDER_PAYMENT_CONFIRM_RESPONSE, OrderPaymentConfirmResponse.newBuilder()
        .setIsSuccess(isSuccess(paymentDone.getPaymentStatus()))
        .setOrderId(paymentDone.getOrderId())
        .setPaymentId(paymentDone.getPaymentKey())
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }
}
