package com.ed.eventservice.events.application.port.out.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JoinEventResponse {

  Long currentParticipants;
}
