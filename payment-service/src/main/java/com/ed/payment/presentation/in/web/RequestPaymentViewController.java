package com.ed.payment.presentation.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RequestPaymentViewController {

  @GetMapping("/payments/widget")
  public String checkout() {
    return "checkout";
  }
}
