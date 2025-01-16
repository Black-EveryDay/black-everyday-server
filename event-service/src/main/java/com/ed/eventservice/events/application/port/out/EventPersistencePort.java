package com.ed.eventservice.events.application.port.out;

import com.ed.eventservice.events.domain.Event;

public interface EventPersistencePort {

  Event saveEvent(Event event);
}
