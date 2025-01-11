package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.domain.PaymentStatus.isSuccess;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_RESPONSE;
import static com.ed.payment.libs.common.exception.ErrorCode.DUPLICATED_ORDER_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.EXPIRED_PAYMENT_CONFIRM_REQUEST;
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
import com.ed.payment.domain.PaymentStatus;
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
    Payment payment = readPaymentPort.getPaymentByOrderPublicId(command.getOrderId());

    validatePayment(payment, command);
    validateCommand(payment, command);

    PaymentDone paymentDone = confirmPaymentPort.confirmPayment(payment, command.getPaymentKey());

    updatePaymentPort.updatePaymentStatusAndPaymentKeyById(
        payment.getPaymentId(), paymentDone.getPaymentStatus(), command.getPaymentKey());

    createPaymentHistoryPort.createConfirmSuccessPaymentHistory(
        payment.getPaymentId(), paymentDone.getLastTransactionKey(), paymentDone.getPaymentStatus(),
        paymentDone.getTotalAmount(), paymentDone.getBalanceAmount());

    sendOrderPaymentConfirmResponse(paymentDone);

    return paymentDone;
  }

  private void validatePayment(Payment payment, ConfirmPaymentCommand command) {
    if (isProcessedPayment(payment)) {
      logBadPaymentRequest(payment, command);
      throw new CustomException(DUPLICATED_ORDER_REQUEST);
    }

    if (isExpiredPaymentRequest(payment, command)) {
      logExpiredPaymentRequest(payment, command);
      throw new CustomException(EXPIRED_PAYMENT_CONFIRM_REQUEST);
    }
  }

  private void validateCommand(Payment payment, ConfirmPaymentCommand command) {
    if (payment.isNotValidAmount(command.getAmount())) {
      transactionHelper.executeInNewTransaction(() -> {
        updatePaymentPort.updatePaymentStatusAndPaymentKeyById(payment.getPaymentId(), VERIFY_FAILED, command.getPaymentKey());
        createPaymentHistoryPort.createFailPaymentHistory(payment.getPaymentId(), VERIFY_FAILED);
      });

      throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
    }
  }

  private boolean isProcessedPayment(Payment payment) {
    PaymentStatus paymentStatus = payment.getPaymentStatus();
    return paymentStatus == DONE || paymentStatus == CANCELED;
  }

  private boolean isExpiredPaymentRequest(Payment payment, ConfirmPaymentCommand command) {
    return payment.getConfirmDeadline().isBefore(command.getRequestDateTime());
  }

  private void sendOrderPaymentConfirmResponse(PaymentDone paymentDone) {
    producer.send(ORDER_PAYMENT_CONFIRM_RESPONSE, OrderPaymentConfirmResponse.newBuilder()
        .setIsSuccess(isSuccess(paymentDone.getPaymentStatus()))
        .setOrderId(paymentDone.getOrderId())
        .setPaymentId(paymentDone.getPaymentKey())
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }

  private void logBadPaymentRequest(Payment payment, ConfirmPaymentCommand command) {
    log.info("Bad Request = userId: {}, orderId: {}, requestDateTime: {}, paymentStatus: {}",
        payment.getUserId(),
        command.getOrderId(),
        command.getRequestDateTime(),
        payment.getPaymentStatus());
  }

  private void logExpiredPaymentRequest(Payment payment, ConfirmPaymentCommand command) {
    log.info("Expired Request = userId: {}, orderId: {}, requestDateTime: {}, confirmDeadline: {}",
        payment.getUserId(),
        command.getOrderId(),
        command.getRequestDateTime(),
        payment.getConfirmDeadline());
  }
}
