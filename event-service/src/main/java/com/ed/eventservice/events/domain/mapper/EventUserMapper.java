package com.ed.eventservice.events.domain.mapper;

import com.ed.eventservice.events.adapter.out.kafka.dto.CreateCouponEvent;
import com.ed.eventservice.events.adapter.out.persistence.entity.EventUserEntity;
import com.ed.eventservice.events.application.port.out.dto.JoinEventResponse;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.EventUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventUserMapper {

  @Mapping(target = "currentParticipants", source = "event.currentParticipants")
  JoinEventResponse domainToJoinEventResponse(EventUser eventUser);

  @Mapping(target = "eventId", source = "event.publicId")
  EventUserEntity domainToJpaEntity(EventUser eventUser);

  @Mapping(target = "id", source = "eventUser.id")
  EventUser jpaEntityToDomain(EventUserEntity eventUser, Event event);

  @Mapping(target = "eventUserId", source = "eventUser.id")
  @Mapping(target = "eventId", source = "eventUser.event.publicId")
  @Mapping(target = "couponTemplateId", source = "eventUser.event.couponTemplateId")
  CreateCouponEvent domainToCreateCouponEvent(EventUser eventUser);
}
