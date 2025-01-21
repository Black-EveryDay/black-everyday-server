package com.ed.eventservice.events.adapter.in.web;

import static com.ed.eventservice.libs.common.ApiResponseUtils.created;

import com.ed.eventservice.events.adapter.in.web.dto.CreateEventRequest;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.in.mapper.CreateEventCommandMapper;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseEntity<CreateEventResponse> createEvent(
      @Valid @RequestBody CreateEventRequest request
  ) {

    CreateEventResponse response =
        eventUseCase.createEvent(createEventCommandMapper.requestToCommand(request));

    return created(response);
  }

}
