package com.ed.orderservice.infrastructure.message.kafka.config;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Topics {
  public static final String ORDER_PAYMENT_REQUEST = "order-payment-request";

}
