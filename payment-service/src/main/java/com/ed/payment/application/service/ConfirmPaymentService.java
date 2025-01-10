package com.ed.payment.application.service;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_RESPONSE;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_AMOUNT_MISMATCH;

import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.mq.OrderPaymentProducer;
import com.ed.payment.infrastructure.out.mq.record.OrderPaymentResponse;
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
  private final OrderPaymentProducer<OrderPaymentResponse> producer;

  @Transactional
  @Override
  public PaymentDone confirmPayment(ConfirmPaymentCommand command) {
    Payment payment = readPaymentPort.findPayment(command.getOrderId());
    verifyRequest(payment, command);
    return confirmPayment(command, payment);
  }

  private void verifyRequest(Payment payment, ConfirmPaymentCommand command) {
    if (payment.isValidAmount(command.getAmount())) {
      return;
    }

    transactionHelper.executeInNewTransaction(() -> updateAfterVerifying(payment, command));
    throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
  }

  private PaymentDone confirmPayment(ConfirmPaymentCommand command, Payment payment) {
    PaymentDone paymentDone = confirmPaymentPort.confirmPayment(payment, command.getPaymentKey());
    PaymentStatus paymentStatus = getPaymentStatus(paymentDone.getStatus());
    updateAfterConfirming(payment, paymentDone, command, paymentStatus);
    sendOrderPaymentResponse(paymentDone);
    return paymentDone;
  }

  private void updateAfterVerifying(Payment payment, ConfirmPaymentCommand command) {
    updatePaymentPort.updatePaymentAfterVerifying(payment.getPaymentId(), VERIFY_FAILED, command.getPaymentKey());
    createPaymentHistoryPort.createFailPaymentHistory(payment.getPaymentId(), VERIFY_FAILED);
  }

  private void updateAfterConfirming(
      Payment payment, PaymentDone paymentDone, ConfirmPaymentCommand command, PaymentStatus paymentStatus) {
    updatePaymentPort.updatePaymentAfterVerifying(payment.getPaymentId(), paymentStatus, command.getPaymentKey());
    createPaymentHistoryPort.createConfirmSuccessPaymentHistory(
        payment.getPaymentId(), paymentDone.getLastTransactionKey(), paymentStatus,
        paymentDone.getTotalAmount(), paymentDone.getBalanceAmount());
  }

  private PaymentStatus getPaymentStatus(String paymentStatus) {
    return isPaymentConfirmed(paymentStatus) ? DONE : ABORTED;
  }

  private boolean isPaymentConfirmed(String paymentStatus) {
    return confirmPaymentPort.isPaymentConfirmed(paymentStatus);
  }

  private void sendOrderPaymentResponse(PaymentDone paymentDone) {
    producer.send(ORDER_PAYMENT_RESPONSE, OrderPaymentResponse.newBuilder()
        .setIsSuccess(isPaymentConfirmed(paymentDone.getStatus()))
        .setOrderPublicId(paymentDone.getOrderId())
        .setPaymentPublicId(paymentDone.getPaymentKey())
        .setMessageTimestamp(LocalDateTime.now())
        .build());
  }
}
