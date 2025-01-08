package com.ed.payment.presentation.in.web;

import com.ed.payment.application.port.in.HandleFailPaymentCommand;
import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HandleFailPaymentController {

  private final HandleFailPaymentUseCase handleFailPaymentUseCase;
  
  @GetMapping("/api/v1/payments/fail")
  public void fail(
      @RequestParam String code, @RequestParam String message,
      @RequestParam String orderId) {

    log.info("Received Request Param: code={}, message={}, orderId={}"
        , code, message, orderId);

    handleFailPaymentUseCase.handleFailPayment(
        HandleFailPaymentCommand.of(code, message, orderId));
  }
}
