package com.ed.eventservice.events.adapter.out.feign.domain.coupon;

import com.ed.eventservice.events.adapter.out.feign.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "coupon-service", configuration = FeignClientConfig.class)
public interface CouponClient {


}
