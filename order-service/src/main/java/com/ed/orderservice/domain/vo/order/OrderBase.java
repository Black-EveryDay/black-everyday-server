package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.libs.common.CommonUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderBase {
  private String orderPublicId;
  private String orderPublicName;

  @Builder
  private OrderBase(String orderPublicId, String orderPublicName) {
    this.orderPublicId = orderPublicId;
    this.orderPublicName = orderPublicName;
  }

  public static String generateOrderPublicId() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    String datePart = dateFormat.format(new Date());
    int randomNumber = CommonUtils.getRandom().nextInt(1000000000);
    String randomPart = String.format("%010d", randomNumber);
    return datePart + randomPart;
  }

  public static String generatePublicName(List<OrderItem> orderItems) {
    return orderItems.stream()
        .findFirst()
        .map(item -> {
          String firstName = item.getProductName();
          long remainingCount = orderItems.size() - 1;
          return remainingCount > 0 ? firstName + " 외 " + remainingCount : firstName;
        })
        .orElse("");
  }


}
