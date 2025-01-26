package com.ed.orderservice.application.port.out;

import com.ed.orderservice.domain.vo.order.Order;
import java.util.List;

public interface OrderGetOutPort {

  List<Order> getOrders(List<String> orderIds);
  Order getOrder(String orderId, String userId);
}
