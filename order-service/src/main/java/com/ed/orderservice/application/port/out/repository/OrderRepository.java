package com.ed.orderservice.application.port.out.repository;

import com.ed.orderservice.domain.vo.Order;

public interface OrderRepository {
	Order findById(String id);
	Order save(Order newOrder);
}
