package com.ed.couponservice.coupon.application.service;

import com.ed.couponservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.couponservice.coupon.application.port.in.CreateCouponCommand;
import com.ed.couponservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.couponservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.couponservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.couponservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import com.ed.couponservice.coupon.domain.Coupon;
import com.ed.couponservice.coupon.domain.CouponTemplate;
import com.ed.couponservice.coupon.domain.mapper.CouponTemplateMapper;
import java.util.List;
import java.util.UUID;
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

    UUID newCouponTemplatePublicId = UUID.randomUUID();

    CouponTemplate newCouponTemplate =
        CouponTemplate.builder()
            .createCouponTemplateDto(
                couponTemplateMapper.commandToCreateDto(createCouponTemplateCommand,
                    newCouponTemplatePublicId))
            .build();

    CouponTemplate savedCouponTemplate =
        couponTemplatePersistencePort.saveCouponTemplate(newCouponTemplate);

    return couponTemplateMapper.domainToResponse(savedCouponTemplate);
  }

  @Override
  @Transactional
  public void createCoupon(CreateCouponCommand command) {

    CouponTemplate couponTemplate =
        couponTemplatePersistencePort.getCouponTemplateByPublicId(command.getCouponTemplateId());

    List<Coupon> newCoupons = couponTemplate.createCoupon(command.getQuantity());

    couponPersistencePort.saveNewCoupons(newCoupons);
  }
}
