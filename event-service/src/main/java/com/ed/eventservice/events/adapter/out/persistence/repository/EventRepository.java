package com.ed.eventservice.events.adapter.out.persistence.repository;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventJpaEntity;
import com.ed.eventservice.events.application.port.out.EventPersistencePort;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.mapper.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventRepository implements EventPersistencePort {

  private final EventJpaRepository eventJpaRepository;
  private final EventMapper eventMapper;

  @Override
  public Event saveEvent(Event event) {
    EventJpaEntity newEventJpaEntity = eventMapper.domainToJpaEntity(event);

    EventJpaEntity savedEventJpaEntity = eventJpaRepository.save(newEventJpaEntity);

    return eventMapper.jpaEntityToDomain(savedEventJpaEntity);
  }
}
