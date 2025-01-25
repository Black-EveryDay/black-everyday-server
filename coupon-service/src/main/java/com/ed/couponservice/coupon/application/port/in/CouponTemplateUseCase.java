package com.ed.couponservice.coupon.application.port.in;

import com.ed.couponservice.coupon.application.port.in.command.CreateCouponCommand;
import com.ed.couponservice.coupon.application.port.in.command.CreateCouponTemplateCommand;
import com.ed.couponservice.coupon.application.port.in.command.CreateEventCouponCommand;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import com.ed.couponservice.coupon.application.port.out.dto.CouponTemplateDetailResponse;
import org.springframework.data.domain.Page;

public interface CouponTemplateUseCase {

  CouponTemplateDetailResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand
  );

  void createCoupon(CreateCouponCommand command);

  void createEventCoupon(CreateEventCouponCommand command);

  Page<CouponTemplateDetailResponse> searchCouponTemplates(SearchCouponTemplatesCommand command);
}
