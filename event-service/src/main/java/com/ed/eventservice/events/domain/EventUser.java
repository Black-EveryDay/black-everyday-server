package com.ed.eventservice.events.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class EventUser {

  private Long id;
  private Event event;
  private UUID userId;
}
