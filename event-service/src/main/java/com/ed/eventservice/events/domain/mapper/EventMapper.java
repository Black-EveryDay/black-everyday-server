package com.ed.eventservice.events.domain.mapper;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventJpaEntity;
import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.domain.Event;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

  @Mapping(target = "publicId", source = "publicId")
  Event createEventCommandToDomain(CreateEventCommand createEventCommand, UUID publicId);

  EventJpaEntity domainToJpaEntity(Event event);

  Event jpaEntityToDomain(EventJpaEntity savedEventJpaEntity);

  CreateEventResponse eventToCreateEventResponse(Event savedEvent);
}
