package com.ed.eventservice.events.application.port.in;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinEventCommand {

  UUID eventId;
  UUID userId;
}
