package com.ed.couponservice.coupon.application.service;

import com.ed.couponservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.couponservice.coupon.application.port.in.command.CreateCouponCommand;
import com.ed.couponservice.coupon.application.port.in.command.CreateCouponTemplateCommand;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import com.ed.couponservice.coupon.application.port.out.CouponPersistencePort;
import com.ed.couponservice.coupon.application.port.out.CouponTemplatePersistencePort;
import com.ed.couponservice.coupon.application.port.out.dto.CouponTemplateDetailResponse;
import com.ed.couponservice.coupon.domain.Coupon;
import com.ed.couponservice.coupon.domain.CouponTemplate;
import com.ed.couponservice.coupon.domain.mapper.CouponTemplateMapper;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  public CouponTemplateDetailResponse createCouponTemplate(
      CreateCouponTemplateCommand createCouponTemplateCommand
  ) {

    UUID newCouponTemplatePublicId = UUID.randomUUID();

    CouponTemplate newCouponTemplate =
        couponTemplateMapper
            .createCouponTemplateCommandToDomain(
                createCouponTemplateCommand,
                newCouponTemplatePublicId
            );

    CouponTemplate savedCouponTemplate =
        couponTemplatePersistencePort.saveCouponTemplate(newCouponTemplate);

    return couponTemplateMapper.domainToCouponTemplateDetailResponse(savedCouponTemplate);
  }

  @Override
  @Transactional
  public void createCoupon(CreateCouponCommand command) {

    CouponTemplate couponTemplate =
        couponTemplatePersistencePort.getCouponTemplateByPublicId(command.getCouponTemplateId());

    List<Coupon> newCoupons = couponTemplate.createCoupon(command.getQuantity());

    couponPersistencePort.saveNewCoupons(newCoupons);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CouponTemplateDetailResponse> searchCouponTemplates(
      SearchCouponTemplatesCommand command) {

    Page<CouponTemplateDetailResponse> searchCouponTemplateResponsePage =
        couponTemplatePersistencePort.searchCouponTemplates(command)
            .map(couponTemplateMapper::domainToCouponTemplateDetailResponse);

    return searchCouponTemplateResponsePage;
  }

}
