package com.ed.payment.presentation.in.web;

import com.ed.payment.application.port.in.ConfirmPaymentCommand;
import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ConfirmPaymentController {

  private final ConfirmPaymentUseCase confirmPaymentUseCase;

  @GetMapping("/api/v1/payments/success")
  public void success(
      @RequestParam String paymentType, @RequestParam String paymentKey,
      @RequestParam String orderId, @RequestParam int amount) throws IOException {

    log.info("Received Request Param: paymentType={}, paymentKey={}, orderId={}, amount={}"
        , paymentType, paymentKey, orderId, amount);

    confirmPaymentUseCase.confirmPayment(
        ConfirmPaymentCommand.of(paymentType, paymentKey, orderId, amount));
  }
}
