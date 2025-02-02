package com.ed.payment.application.port.out.pg;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;
import java.io.IOException;

public interface CancelPaymentPort {
  PaymentCanceledResponse cancelPayment(String paymentKey, String idempotencyKey, CancelPaymentRequest request) throws IOException;
}
