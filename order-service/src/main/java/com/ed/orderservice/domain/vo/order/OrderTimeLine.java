package com.ed.orderservice.domain.vo.order;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderTimeLine {

  private static final ZoneId ASIA_SEOUL = ZoneId.of("Asia/Seoul");
  private static final long PAYMENT_DEADLINE_HOURS = 48;
  private static final long ORDER_CANCEL_DEADLINE_HOURS = 72;

  private LocalDateTime orderDate;
  private LocalDateTime paymentDeadline;
  private LocalDateTime orderCancelDeadline;

  public void updateOrderTimelines() {
    ZonedDateTime now = ZonedDateTime.now(ASIA_SEOUL);
    this.orderDate = toLocalDateTime(now);
    this.paymentDeadline = toLocalDateTime(now.plusHours(PAYMENT_DEADLINE_HOURS));
    this.orderCancelDeadline = toLocalDateTime(now.plusHours(ORDER_CANCEL_DEADLINE_HOURS));
  }

  private LocalDateTime toLocalDateTime(ZonedDateTime dateTime) {
    return dateTime.toLocalDateTime();
  }

}
