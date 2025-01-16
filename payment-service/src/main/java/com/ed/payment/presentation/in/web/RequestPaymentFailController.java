package com.ed.payment.presentation.in.web;

import static org.springframework.http.ResponseEntity.ok;

import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import com.ed.payment.application.port.out.pg.dtos.PaymentFailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RequestPaymentFailController {

  private final InPortMapper inPortMapper;
  private final HandleFailPaymentUseCase handleFailPaymentUseCase;
  
  @GetMapping("/api/v1/payments/fail")
  public ResponseEntity<PaymentFailResponse> requestPaymentFail(
      @RequestParam String code, @RequestParam String message, @RequestParam String orderId) {
    return ok(handleFailPaymentUseCase.handleFailPayment(
        inPortMapper.failPaymentToInPort(code, message, orderId)));
  }
}
