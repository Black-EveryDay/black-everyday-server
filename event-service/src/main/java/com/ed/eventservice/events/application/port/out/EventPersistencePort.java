package com.ed.eventservice.events.application.port.out;

import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.EventUser;
import java.util.UUID;

public interface EventPersistencePort {

  Event saveEvent(Event event);

  Event findEventById(UUID eventId);

  EventUser createEventUser(EventUser newEventUser);

  boolean checkUserAlreadyJoinedEvent(UUID eventId, UUID userId);
}
