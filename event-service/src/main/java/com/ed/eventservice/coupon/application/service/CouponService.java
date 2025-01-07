package com.ed.eventservice.coupon.application.service;

import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.eventservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.eventservice.coupon.domain.CouponTemplate;
import com.ed.eventservice.coupon.domain.mapper.CouponTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService implements CouponUseCase {

  private final CouponTemplatePersistencePort couponTemplatePersistencePort;
  private final CouponTemplateMapper couponTemplateMapper;

  @Override
  @Transactional
  public void createCouponTemplate(CreateCouponTemplateCommand createCouponTemplateCommand) {
    CouponTemplate couponTemplate = couponTemplateMapper.commandToDomain(
        createCouponTemplateCommand);
    couponTemplatePersistencePort.saveCouponTemplate(couponTemplate);
  }
}
