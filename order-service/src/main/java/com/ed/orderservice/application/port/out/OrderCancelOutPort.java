package com.ed.orderservice.application.port.out;

import com.ed.orderservice.domain.vo.order.Order;

public interface OrderCancelOutPort {

  Order save(Order newOrder);
  
}
