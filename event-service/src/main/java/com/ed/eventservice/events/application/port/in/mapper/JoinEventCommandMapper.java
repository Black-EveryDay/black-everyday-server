package com.ed.eventservice.events.application.port.in.mapper;

import com.ed.eventservice.events.adapter.in.web.dto.JoinEventRequest;
import com.ed.eventservice.events.application.port.in.JoinEventCommand;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JoinEventCommandMapper {

  JoinEventCommand eventIdAndRequestToCommand(UUID eventId, JoinEventRequest request);
}
