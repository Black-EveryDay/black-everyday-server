package com.ed.eventservice.events.application.service;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.CouponTemplateDetailResponse;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuanceType;
import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.in.JoinEventCommand;
import com.ed.eventservice.events.application.port.out.CouponOutPort;
import com.ed.eventservice.events.application.port.out.EventOutPort;
import com.ed.eventservice.events.application.port.out.EventPersistencePort;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.application.port.out.dto.JoinEventResponse;
import com.ed.eventservice.events.domain.Event;
import com.ed.eventservice.events.domain.EventUser;
import com.ed.eventservice.events.domain.mapper.EventMapper;
import com.ed.eventservice.events.domain.mapper.EventUserMapper;
import com.ed.eventservice.libs.exception.ExceptionStatus;
import com.ed.eventservice.libs.exception.ServiceException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService implements EventUseCase {

  private final EventMapper eventMapper;
  private final EventUserMapper eventUserMapper;
  private final EventPersistencePort eventPersistencePort;
  private final CouponOutPort couponOutPort;
  private final EventOutPort eventOutPort;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  @Transactional
  public CreateEventResponse createEvent(CreateEventCommand createEventCommand) {

    validateCouponTemplate(createEventCommand.getCouponTemplateId());

    UUID newPublicId = UUID.randomUUID();
    Event newEvent = eventMapper.createEventCommandToDomain(createEventCommand, newPublicId);
    Event savedEvent = eventPersistencePort.saveEvent(newEvent);

    return eventMapper.eventToCreateEventResponse(savedEvent);
  }

  private void validateCouponTemplate(UUID couponTemplateId) {
    List<CouponTemplateDetailResponse> response = couponOutPort.getCouponTemplateById(
        couponTemplateId);

    if (response.isEmpty()) {

      throw new ServiceException(ExceptionStatus.COUPON_TEMPLATE_NOT_FOUND);
    }

    if (!Objects.equals(
        CouponIssuanceType.AUTOMATIC,
        response.getFirst().getCouponIssuanceType()
    )) {

      throw new ServiceException(ExceptionStatus.COUPON_TEMPLATE_ISSUANCE_TYPE_NOT_AUTOMATIC);
    }
  }

  @Override
  @Transactional
  public JoinEventResponse joinEvent(JoinEventCommand joinEventCommand) {

    validateUserNotJoinedEvent(joinEventCommand.getEventId(), joinEventCommand.getUserId());

    Event event = eventPersistencePort.findEventById(joinEventCommand.getEventId());

    validateParticipationPossible(event);

    EventUser newEventUser = event.join(joinEventCommand.getUserId());
    EventUser savedEventUser = eventPersistencePort.createEventUser(newEventUser);

    applicationEventPublisher.publishEvent(
        eventUserMapper.domainToCreateCouponEvent(savedEventUser));

    return eventUserMapper.domainToJoinEventResponse(savedEventUser);
  }

  private void validateUserNotJoinedEvent(UUID eventId, UUID userId) {

    if (eventPersistencePort.checkUserAlreadyJoinedEvent(eventId, userId)) {

      throw new ServiceException(ExceptionStatus.USER_ALREADY_JOINED_EVENT);
    }
  }

  private void validateParticipationPossible(Event event) {

    if (!eventOutPort.checkParticipationPossible(event)) {

      throw new ServiceException(ExceptionStatus.EVENT_FULL);
    }
  }
}
