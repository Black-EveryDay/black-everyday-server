package com.ed.couponservice.coupon.adapter.in.kafka.consumer;

import com.ed.EventCouponCreateRequest;
import com.ed.couponservice.coupon.adapter.in.kafka.topic.KafkaTopics;
import com.ed.couponservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.couponservice.coupon.application.port.in.command.mapper.CreateEventCouponCommandMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventCouponCreateConsumer {

  private final CouponTemplateUseCase couponTemplateUseCase;
  private final CreateEventCouponCommandMapper createEventCouponCommandMapper;

  @KafkaListener(topics = KafkaTopics.EVENT_COUPON_CREATE_REQUEST)
  public void consumeCreateCoupon(EventCouponCreateRequest request) {

    logConsumerRecord(request);

    couponTemplateUseCase.createEventCoupon(
        createEventCouponCommandMapper.eventCouponCreateRequestToCommand(request));
  }

  void logConsumerRecord(EventCouponCreateRequest request) {
    log.info(
        "[EventCouponCreateConsumer] [consumeCreateCoupon] couponTemplateId ::: {}, userId ::: {}, eventId ::: {}",
        request.getCouponTemplateId(),
        request.getUserId(),
        request.getEventId());
  }
}
