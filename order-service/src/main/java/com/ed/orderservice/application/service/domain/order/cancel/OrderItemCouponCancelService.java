package com.ed.orderservice.application.service.domain.order.cancel;

import com.ed.orderservice.application.port.in.CouponUseInPort;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemCouponCancelService {

  private final CouponUseInPort couponUseInPort;

  public void cancelCoupons(Order order) {
    order.getOrderItems().forEach(item -> {
      OrderItemCoupon itemCoupon = item.getOrderItemCoupon();
      if (itemCoupon != null && itemCoupon.getOrderCouponPublicId() != null) {
          UUID couponPublicId = UUID.fromString(itemCoupon.getOrderCouponPublicId());
          ApiResponse<UseCouponResponse> response = couponUseInPort.cancelUseCoupon(couponPublicId);
      }
    });

  }
}


