package com.ed.eventservice.events.application.port.in.mapper;

import com.ed.eventservice.events.adapter.in.web.dto.CreateEventRequest;
import com.ed.eventservice.events.application.port.in.CreateEventCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateEventCommandMapper {

  CreateEventCommand requestToCommand(CreateEventRequest createEventRequest);
}
