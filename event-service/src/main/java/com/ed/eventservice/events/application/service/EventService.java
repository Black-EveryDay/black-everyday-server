package com.ed.eventservice.events.application.service;

import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.out.EventPersistencePort;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.mapper.EventMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService implements EventUseCase {

  private final EventMapper eventMapper;
  private final EventPersistencePort eventPersistencePort;

  @Override
  @Transactional
  public CreateEventResponse createEvent(CreateEventCommand createEventCommand) {

    UUID newPublicId = UUID.randomUUID();

    Event newEvent = eventMapper.createEventCommandToDomain(createEventCommand, newPublicId);

    Event savedEvent = eventPersistencePort.saveEvent(newEvent);

    return eventMapper.eventToCreateEventResponse(savedEvent);
  }
}
