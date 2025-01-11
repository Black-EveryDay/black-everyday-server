package com.ed.payment.libs.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTopics {
  public static final String ORDER_PAYMENT_CONFIRM_REQUEST = "order_payment_confirm_request";
  public static final String ORDER_PAYMENT_CONFIRM_RESPONSE = "order_payment_confirm_response";
}
