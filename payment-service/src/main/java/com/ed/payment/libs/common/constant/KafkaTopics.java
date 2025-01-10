package com.ed.payment.libs.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTopics {
  public static final String ORDER_PAYMENT_REQUEST = "order-payment-request";
  public static final String ORDER_PAYMENT_RESPONSE = "order-payment-response";
}
