package com.ed.eventservice.events.application.port.out;

import com.ed.eventservice.events.domain.Event;

public interface EventOutPort {

  boolean checkParticipationPossible(Event eventId);
}
