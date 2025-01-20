package com.ed.couponservice.coupon.application.port.in.command.mapper;

import com.ed.couponservice.coupon.adapter.in.web.dto.SearchCouponTemplatesCondition;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Pageable;

@Mapper(componentModel = "spring")
public interface CommandMapper {


  SearchCouponTemplatesCommand searchCouponTemplateRequestToSearchCouponTemplateCommand(
      SearchCouponTemplatesCondition searchCouponTemplatesCondition,
      Pageable pageable
  );
}
