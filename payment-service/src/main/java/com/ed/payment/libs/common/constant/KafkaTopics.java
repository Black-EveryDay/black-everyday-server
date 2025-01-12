package com.ed.payment.libs.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTopics {
  public static final String ORDER_PAYMENT_CREATE_REQUEST = "order_payment_create_request";
  public static final String ORDER_PAYMENT_CONFIRM_RESPONSE = "order_payment_confirm_response";
  public static final String ORDER_PAYMENT_CANCEL_REQUEST = "order_payment_cancel_request";
  public static final String ORDER_PAYMENT_CANCEL_RESPONSE = "order_payment_cancel_response";
}
