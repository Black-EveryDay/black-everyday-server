package com.ed.eventservice.events.adapter.out.kafka.producer;

import com.ed.EventCouponCreateRequest;
import com.ed.eventservice.events.adapter.out.kafka.dto.CreateCouponEvent;
import com.ed.eventservice.events.adapter.out.kafka.topic.Topics;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponMessageProducer {

  private final KafkaTemplate<String, EventCouponCreateRequest> kafkaTemplate;

  public void sendCouponCreateRequest(CreateCouponEvent createCouponEvent) {

    EventCouponCreateRequest eventCouponCreateRequest = EventCouponCreateRequest.newBuilder()
        .setCouponTemplateId(createCouponEvent.getCouponTemplateId().toString())
        .setUserId(createCouponEvent.getUserId().toString())
        .setEventId(createCouponEvent.getEventId().toString())
        .setRequestDateTime(LocalDateTime.now())
        .build();

    try {

      kafkaTemplate.send(Topics.EVENT_COUPON_CREATE_REQUEST,
          String.valueOf(createCouponEvent.getEventUserId()), eventCouponCreateRequest);

      log.info("Create coupon request sent for eventUser: {}",
          createCouponEvent.getEventUserId());
    } catch (Exception e) {

      log.error("Failed to send create coupon request for eventUser: {}",
          createCouponEvent.getEventUserId(), e);

      throw new RuntimeException("Failed to send payment confirm request", e);
    }
  }

}
