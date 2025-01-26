package com.ed.couponservice.coupon.adapter.in.kafka.topic;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class KafkaTopics {

  public static final String EVENT_COUPON_CREATE_REQUEST = "event-coupon-create-request";
}
