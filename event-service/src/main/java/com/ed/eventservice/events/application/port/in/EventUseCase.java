package com.ed.eventservice.events.application.port.in;

import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;

public interface EventUseCase {

  CreateEventResponse createEvent(CreateEventCommand createEventCommand);
}
