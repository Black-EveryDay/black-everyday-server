package com.ed.eventservice.events.adapter.in.web;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.eventservice.events.adapter.in.web.dto.CreateEventRequest;
import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import com.ed.eventservice.events.application.port.in.EventUseCase;
import com.ed.eventservice.events.application.port.in.mapper.CreateEventCommandMapper;
import com.ed.eventservice.events.application.port.out.dto.CreateEventResponse;
import com.ed.eventservice.events.domain.enums.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(EventController.class)
class EventControllerTest {

  @MockitoBean
  CreateEventCommandMapper createEventCommandMapper;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private EventUseCase eventUseCase;
  @MockitoBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @Nested
  @DisplayName("createEventTest")
  class createEventTest {

    private final String uri = "/api/v1/events";

    @Test
    @DisplayName("should create event success")
    void shouldCreateEventSuccess() throws Exception {

      // given
      CreateEventRequest createEventRequest = CreateEventRequest.builder()
          .name("Rush Event")
          .couponTemplateId(UUID.randomUUID())
          .type(EventType.RUSH_EVENT)
          .startAt(LocalDateTime.now())
          .endAt(LocalDateTime.now().plusDays(30))
          .build();

      CreateEventCommand createEventCommand = CreateEventCommand.builder()
          .name(createEventRequest.getName())
          .couponTemplateId(createEventRequest.getCouponTemplateId())
          .type(createEventRequest.getType())
          .startAt(createEventRequest.getStartAt())
          .endAt(createEventRequest.getEndAt())
          .build();

      CreateEventResponse createEventResponse = CreateEventResponse.builder()
          .publicId(UUID.randomUUID())
          .name(createEventRequest.getName())
          .couponTemplateId(createEventRequest.getCouponTemplateId())
          .type(createEventRequest.getType())
          .startAt(createEventRequest.getStartAt())
          .endAt(createEventRequest.getEndAt())
          .build();

      given(createEventCommandMapper.requestToCommand(createEventRequest)).willReturn(
          createEventCommand);
      given(eventUseCase.createEvent(createEventCommand)).willReturn(createEventResponse);

      // when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createEventRequest)));

      // then
      resultActions.andExpect(status().isCreated());
    }
  }
}