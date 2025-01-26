package com.ed.couponservice.coupon.application.port.in.command.mapper;

import com.ed.EventCouponCreateRequest;
import com.ed.couponservice.coupon.application.port.in.command.CreateEventCouponCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateEventCouponCommandMapper {

  CreateEventCouponCommand eventCouponCreateRequestToCommand(EventCouponCreateRequest request);
}
