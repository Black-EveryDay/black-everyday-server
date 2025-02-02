package com.ed.payment.application.port.out.pg;

import com.ed.payment.application.port.out.pg.dtos.ConfirmPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import java.io.IOException;

public interface ConfirmPaymentPort {
  PaymentDoneResponse confirmPayment(String idempotencyKey, ConfirmPaymentRequest request) throws IOException;
}
