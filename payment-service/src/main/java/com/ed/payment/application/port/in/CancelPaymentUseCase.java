package com.ed.payment.application.port.in;

import com.ed.OrderPaymentCancelRequestEvent;
import java.io.IOException;

public interface CancelPaymentUseCase {
  void cancelPayment(OrderPaymentCancelRequestEvent request) throws IOException;
}
