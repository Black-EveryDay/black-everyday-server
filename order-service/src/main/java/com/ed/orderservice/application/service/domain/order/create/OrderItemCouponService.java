package com.ed.orderservice.application.service.domain.order.create;

import com.ed.orderservice.application.port.in.CouponUseInPort;
import com.ed.orderservice.domain.mapper.OrderMapper;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponDto;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponRequest;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponResponse;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.dto.UseCouponResponse.CouponTemplateResponse;
import com.ed.orderservice.libs.response.ApiResponse;
import feign.FeignException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemCouponService {

  private final OrderMapper orderMapper;
  private final CouponUseInPort couponUseInPort;

  public Order applyCoupons(Order order) {
    return orderMapper.toUseCouponDto(order)
        .stream()
        .reduce(order, this::processUseCoupon, (o1, o2) -> o1);
  }

  private Order processUseCoupon(Order order, UseCouponDto dto) {
    try {
      ApiResponse<UseCouponResponse> apiResponse = couponUseInPort.useCoupon(
          dto.getCouponPublicId(), createUseCouponRequest(dto));
      return updateOrderWithCoupon(order, dto, apiResponse.getBody());

    } catch (FeignException e) {
      log.error("Coupon use failed due to FeignException: {}", e.getMessage());
      throw new RuntimeException("쿠폰 서비스 호출 중 오류가 발생했습니다", e);
    }
  }

  private UseCouponRequest createUseCouponRequest(UseCouponDto dto) {
    return UseCouponRequest.builder()
        .userId(dto.getUserId())
        .brandId(dto.getBrandId())
        .productId(dto.getProductId())
        .orderId(dto.getOrderId())
        .build();
  }

  private Order updateOrderWithCoupon(Order order, UseCouponDto dto, UseCouponResponse response) {
    order.getOrderItems().stream()
        .filter(item -> isCouponMatchingOrderItem(item, dto))
        .findFirst()
        .ifPresent(item -> item.updateOrderItemCoupon(createUpdatedOrderItemCoupon(response)));

    return order;
  }

  private boolean isCouponMatchingOrderItem(OrderItem item, UseCouponDto dto) {
    return Optional.ofNullable(item.getOrderItemCoupon())
        .map(coupon -> coupon.getOrderCouponPublicId().equals(dto.getCouponPublicId().toString()))
        .orElse(false);
  }

  private OrderItemCoupon createUpdatedOrderItemCoupon(UseCouponResponse response) {
    return OrderItemCoupon.builder()
        .orderCouponPublicId(response.couponId().toString())
        .orderItemCouponTemplate(createOrderItemCouponTemplate(response.couponTemplate()))
        .build();
  }

  private OrderItemCouponTemplate createOrderItemCouponTemplate(CouponTemplateResponse template) {
    return OrderItemCouponTemplate.builder()
        .couponTemplateId(template.templateId().toString())
        .couponName(template.couponName())
        .discountType(template.discountType())
        .discountAmount(template.discountValue())
        .build();
  }

}
