package com.ed.eventservice.events.adapter.out.feign.domain.coupon;

import com.ed.eventservice.events.adapter.out.feign.config.FeignClientConfig;
import com.ed.eventservice.events.adapter.out.feign.domain.coupon.dto.CouponTemplateDetailResponse;
import com.ed.eventservice.libs.common.ApiResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "coupon-service", configuration = FeignClientConfig.class)
public interface CouponClient {

  @GetMapping("/api/v1/coupon-templates?publicId={publicId}")
  ApiResponse<Page<CouponTemplateDetailResponse>> searchCouponTemplates(
      @PathVariable UUID publicId
  );

}
