package com.ed.eventservice.events.adapter.in.web;

import static com.ed.eventservice.libs.common.ApiResponseUtils.created;

import com.ed.eventservice.events.adapter.in.web.dto.CreateEventRequest;
import com.ed.eventservice.events.adapter.in.web.dto.JoinEventRequest;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.in.mapper.CreateEventCommandMapper;
import com.ed.eventservice.events.application.port.in.mapper.JoinEventCommandMapper;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.application.port.out.dto.JoinEventResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

  private final EventUseCase eventUseCase;
  private final CreateEventCommandMapper createEventCommandMapper;
  private final JoinEventCommandMapper joinEventCommandMapper;

  @PostMapping
  public ResponseEntity<CreateEventResponse> createEvent(
      @Valid @RequestBody CreateEventRequest request
  ) {

    CreateEventResponse response =
        eventUseCase.createEvent(createEventCommandMapper.requestToCommand(request));

    return created(response);
  }

  @PostMapping("/{eventId}/join")
  public JoinEventResponse joinEvent(
      @PathVariable UUID eventId,
      @RequestBody JoinEventRequest request
  ) {

    return eventUseCase.joinEvent(
        joinEventCommandMapper.eventIdAndRequestToCommand(eventId, request));
  }

}
