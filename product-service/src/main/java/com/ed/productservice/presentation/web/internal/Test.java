package com.ed.productservice.presentation.web.internal;

import com.ed.productservice.application.service.internal.ProductInternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/order")
@RestController
public class Test {
  private final ProductInternalService productInternalService;


}
