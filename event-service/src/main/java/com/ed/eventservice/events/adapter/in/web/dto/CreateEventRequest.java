package com.ed.eventservice.events.adapter.in.web.dto;

import com.ed.eventservice.events.domain.enums.EventType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateEventRequest {

  @NotEmpty
  private String name;
  @NotNull
  private EventType type;
  @NotNull
  private LocalDateTime startAt;
  @NotNull
  private LocalDateTime endAt;
  @NotNull
  private UUID couponTemplateId;
}
