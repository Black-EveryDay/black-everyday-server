package com.ed.orderservice.domain.vo.order.event;

import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderEvent;

public abstract class BaseOrderEvent  {
  protected Order order;
  protected OrderEvent orderEvent;
}
