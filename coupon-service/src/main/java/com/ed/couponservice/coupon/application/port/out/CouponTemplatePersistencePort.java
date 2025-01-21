package com.ed.couponservice.coupon.application.port.out;

import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import com.ed.couponservice.coupon.domain.CouponTemplate;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CouponTemplatePersistencePort {

  CouponTemplate saveCouponTemplate(CouponTemplate couponTemplate);

  CouponTemplate getCouponTemplateByPublicId(UUID couponTemplateId);

  Page<CouponTemplate> searchCouponTemplates(SearchCouponTemplatesCommand command);
}
