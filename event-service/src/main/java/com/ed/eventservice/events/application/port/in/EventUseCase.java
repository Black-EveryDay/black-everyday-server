package com.ed.eventservice.events.application.port.in;

import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.application.port.out.dto.JoinEventResponse;

public interface EventUseCase {

  CreateEventResponse createEvent(CreateEventCommand createEventCommand);

  JoinEventResponse joinEvent(JoinEventCommand joinEventCommand);
}
