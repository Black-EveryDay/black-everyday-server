package com.ed.eventservice.events.adapter.out.persistence.repository;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventJpaEntity;
import com.ed.eventservice.events.adapter.out.persistence.entity.EventUserEntity;
import com.ed.eventservice.events.application.port.out.EventPersistencePort;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.EventUser;
import com.ed.eventservice.events.domain.mapper.EventMapper;
import com.ed.eventservice.events.domain.mapper.EventUserMapper;
import com.ed.eventservice.libs.exception.AdapterException;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepository implements EventPersistencePort {

  private final EventJpaRepository eventJpaRepository;
  private final EventUserJpaRepository eventUserJpaRepository;
  private final EventMapper eventMapper;
  private final EventUserMapper eventUserMapper;

  @Override
  public Event saveEvent(Event event) {
    EventJpaEntity newEventJpaEntity = eventMapper.domainToJpaEntity(event);

    EventJpaEntity savedEventJpaEntity = eventJpaRepository.save(newEventJpaEntity);

    return eventMapper.jpaEntityToDomain(savedEventJpaEntity);
  }

  @Override
  public Event findEventById(UUID eventId) {

    EventJpaEntity eventJpaEntity = eventJpaRepository.findByPublicId(eventId.toString())
        .orElseThrow(() -> new AdapterException(ExceptionStatus.EVENT_NOT_FOUND));

    return eventMapper.jpaEntityToDomain(eventJpaEntity);
  }

  @Override
  public EventUser createEventUser(EventUser newEventUser) {

    Event savedEvent = saveEvent(newEventUser.getEvent());

    EventUserEntity savedEventUser = eventUserJpaRepository.save(
        eventUserMapper.domainToJpaEntity(newEventUser));

    return eventUserMapper.jpaEntityToDomain(savedEventUser, savedEvent);
  }
}
