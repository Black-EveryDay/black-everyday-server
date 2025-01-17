package com.ed.couponservice.coupon.application.port.in.command.mapper;

import com.ed.couponservice.coupon.adapter.in.web.dto.CouponSearchCondition;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CommandMapper {


  SearchCouponTemplatesCommand searchCouponTemplateRequestToSearchCouponTemplateCommand(
      CouponSearchCondition couponSearchCondition,
      Pageable pageable
  );
}
