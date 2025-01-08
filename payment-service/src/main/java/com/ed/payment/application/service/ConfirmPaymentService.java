package com.ed.payment.application.service;

import static com.ed.payment.infrastructure.out.persistence.PaymentStatus.ABORTED;
import static com.ed.payment.infrastructure.out.persistence.PaymentStatus.DONE;
import static com.ed.payment.infrastructure.out.persistence.PaymentStatus.VERIFY_FAILED;
import static com.ed.payment.libs.common.ErrorCode.PAYMENT_AMOUNT_MISMATCH;

import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.pg.ConfirmPaymentPort;
import com.ed.payment.application.port.out.pg.PaymentDone;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.PaymentStatus;
import com.ed.payment.libs.common.CustomException;
import com.ed.payment.libs.common.TransactionHelper;
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

  @Transactional
  @Override
  public void confirmPayment(ConfirmPaymentCommand command) {
    Payment payment = readPaymentPort.findPayment(command.getOrderId());
    verifyRequest(payment, command);

    PaymentDone paymentDone = confirmPaymentPort.confirmPayment(
        payment, command.getPaymentKey());
    PaymentStatus paymentStatus = getPaymentStatus(paymentDone.getStatus());
    updatePaymentAndPaymentHistory(command, payment, paymentStatus);

    // todo
    // kafka producer 로 값 넣어주기 ==> success 여부 정도 예상된다.
    // 결제 실패 시 => order 로 바로 쏴주면 너무 복잡해짐
    //            => 하나의 주문에 대해서 언제까지 결제를 해야하는지 고객한테 알려줘서
    // scheduler  => 상태 전이 + publish(정책) => @Scheduler 를 통해서

  }

  private void verifyRequest(Payment payment, ConfirmPaymentCommand command) {
    if (payment.isValidAmount(command.getAmount())) {
      return;
    }

    transactionHelper.executeInNewTransaction(
        () -> updatePaymentAndPaymentHistory(command, payment, VERIFY_FAILED));
    throw new CustomException(PAYMENT_AMOUNT_MISMATCH);
  }

  private PaymentStatus getPaymentStatus(String paymentStatus) {
    return isPaymentConfirmed(paymentStatus) ? DONE : ABORTED;
  }

  private boolean isPaymentConfirmed(String paymentStatus) {
    return confirmPaymentPort.isPaymentConfirmed(paymentStatus);
  }

  private void updatePaymentAndPaymentHistory(
      ConfirmPaymentCommand command, Payment payment, PaymentStatus paymentStatus) {
    updatePaymentPort.updatePaymentAfterVerifying(
        payment.getPaymentId(), command.getPaymentKey(), paymentStatus);
    createPaymentHistoryPort.createPaymentHistory(
        payment.getPaymentId(), payment.getAmount(), paymentStatus);
  }
}
