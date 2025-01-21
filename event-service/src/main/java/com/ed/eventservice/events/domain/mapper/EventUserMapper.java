package com.ed.eventservice.events.domain.mapper;

import com.ed.eventservice.events.adapter.out.persistence.entity.EventUserEntity;
import com.ed.eventservice.events.application.port.out.dto.JoinEventResponse;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.EventUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventUserMapper {

  @Mapping(target = "currentParticipants", source = "event.currentParticipants")
  JoinEventResponse domainToJoinEventResponse(EventUser savedEventUser);

  @Mapping(target = "eventId", source = "event.publicId")
  EventUserEntity domainToJpaEntity(EventUser newEventUser);

  EventUser jpaEntityToDomain(EventUserEntity savedEventUser, Event savedEvent);
}
