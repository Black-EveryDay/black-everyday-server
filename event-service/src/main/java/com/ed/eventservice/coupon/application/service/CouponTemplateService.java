package com.ed.eventservice.coupon.application.service;

import com.ed.eventservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.eventservice.coupon.application.port.in.CreateCouponCommand;
import com.ed.eventservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.eventservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.eventservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.eventservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import com.ed.eventservice.coupon.domain.Coupon;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.mapper.CouponTemplateMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponTemplateService implements CouponTemplateUseCase {

  private final CouponTemplatePersistencePort couponTemplatePersistencePort;
  private final CouponPersistencePort couponPersistencePort;
  private final CouponTemplateMapper couponTemplateMapper;

  @Override
  @Transactional
  public CreateCouponTemplateResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand
  ) {

    CouponTemplate newCouponTemplate =
        CouponTemplate.builder()
            .createCouponTemplateDto(
                couponTemplateMapper.commandToCreateDto(createCouponTemplateCommand))
            .build();

    CouponTemplate savedCouponTemplate =
        couponTemplatePersistencePort.saveCouponTemplate(newCouponTemplate);

    return couponTemplateMapper.domainToResponse(savedCouponTemplate);
  }

  @Override
  public void createCoupon(CreateCouponCommand command) {

    CouponTemplate couponTemplate =
        couponTemplatePersistencePort.getCouponTemplateByPublicId(command.getCouponTemplateId());

    List<Coupon> newCoupons = couponTemplate.createCoupon(command.getQuantity());

    couponPersistencePort.saveNewCoupons(newCoupons);
  }
}
