package com.ed.payment.presentation.in.web;

import com.ed.payment.application.port.in.command.PaymentRequestFailCommand;
import org.springframework.stereotype.Component;

@Component
class InPortMapper {

  PaymentRequestFailCommand failPaymentToInPort(String code, String message, String orderId) {
    return PaymentRequestFailCommand.builder()
        .code(code)
        .message(message)
        .orderId(orderId)
        .build();
  }
}
