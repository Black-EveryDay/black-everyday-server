package com.ed.eventservice.events.application.port.in;

import com.ed.eventservice.events.domain.enums.EventType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateEventCommand {

  private String name;
  private EventType type;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private UUID couponTemplateId;
  private Long maxParticipants;
}
