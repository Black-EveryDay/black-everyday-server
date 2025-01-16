package com.ed.eventservice.events.domain;

import com.ed.eventservice.events.domain.enums.EventType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Event {

  private Long id;
  private UUID publicId;
  private String name;
  private EventType type;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private UUID couponTemplateId;
}
