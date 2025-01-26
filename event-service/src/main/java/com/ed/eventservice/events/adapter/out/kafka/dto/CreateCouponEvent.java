package com.ed.eventservice.events.adapter.out.kafka.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCouponEvent {

  private final Long eventUserId;
  private final UUID couponTemplateId;
  private final UUID userId;
  private final UUID eventId;
}
