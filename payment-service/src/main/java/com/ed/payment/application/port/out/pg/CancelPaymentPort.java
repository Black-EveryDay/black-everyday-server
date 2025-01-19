package com.ed.payment.application.port.out.pg;

import com.ed.payment.application.port.out.pg.dtos.CancelPaymentRequest;
import com.ed.payment.application.port.out.pg.dtos.PaymentCanceledResponse;

public interface CancelPaymentPort {
  PaymentCanceledResponse cancelPayment(CancelPaymentRequest request);
}
