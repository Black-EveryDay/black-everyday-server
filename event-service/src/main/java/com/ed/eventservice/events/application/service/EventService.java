package com.ed.eventservice.events.application.service;

import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.CouponTemplateDetailResponse;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.enums.CouponIssuanceType;
import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.in.JoinEventCommand;
import com.ed.eventservice.events.application.port.out.CouponOutPort;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService implements EventUseCase {

  private final EventMapper eventMapper;
  private final EventUserMapper eventUserMapper;
  private final EventPersistencePort eventPersistencePort;
  private final CouponOutPort couponOutPort;

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
    Event event = eventPersistencePort.findEventById(joinEventCommand.getEventId());

    EventUser newEventUser = event.join(joinEventCommand.getUserId());
    EventUser savedEventUser = eventPersistencePort.createEventUser(newEventUser);

    return eventUserMapper.domainToJoinEventResponse(savedEventUser);
  }
}
