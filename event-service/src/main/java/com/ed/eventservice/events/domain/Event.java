package com.ed.eventservice.events.domain;

import com.ed.eventservice.events.domain.enums.EventType;
import com.ed.eventservice.libs.exception.DomainException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Event implements Serializable {

  private Long id;
  private UUID publicId;
  private String name;
  private EventType type;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Long maxParticipants;
  private long currentParticipants;
  private UUID couponTemplateId;

  public EventUser join(UUID userId) {

    validateEventAvailableAtCurrent();

    currentParticipants += 1;

    return EventUser.builder()
        .event(this)
        .userId(userId)
        .build();
  }

  private void validateEventAvailableAtCurrent() {

    if (startAt.isAfter(LocalDateTime.now()) || endAt.isBefore(LocalDateTime.now())) {

      throw new DomainException(ExceptionStatus.EVENT_NOT_AVAILABLE);
    }
  }
}
