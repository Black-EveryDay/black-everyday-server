package com.ed.payment.application.service;

import static com.ed.payment.infrastructure.out.persistence.PaymentStatus.ABORTED;
import static com.ed.payment.libs.common.ErrorCode.DUPLICATED_ORDER_REQUEST;

import com.ed.payment.application.port.in.HandleFailPaymentCommand;
import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.libs.common.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HandlerFailPaymentService implements HandleFailPaymentUseCase {

  private static final String DUPLICATED_ORDER_ERROR = "DUPLICATED_ORDER_ID";

  private final ReadPaymentPort readPaymentPort;
  private final UpdatePaymentPort updatePaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;

  @Transactional
  @Override
  public void handleFailPayment(HandleFailPaymentCommand command) {

    if (DUPLICATED_ORDER_ERROR.equalsIgnoreCase(command.getCode())) {
      throw new CustomException(DUPLICATED_ORDER_REQUEST);
    }

    Payment payment = readPaymentPort.findPayment(command.getOrderId());
    updatePaymentPort.updatePaymentStatus(payment.getPaymentId(), ABORTED);
    createPaymentHistoryPort.createPaymentHistory(
        payment.getPaymentId(), payment.getAmount(), ABORTED);

    // todo
    // kafka producer 로 값 넣어주기 ==> success 여부 정도 예상된다.
    

  }
}
