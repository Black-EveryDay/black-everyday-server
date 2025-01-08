package com.ed.orderservice.infrastructure.db.mysql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ed.orderservice.infrastructure.entity.OrderEntity;

@Repository
public interface OrderJpaRepository
	extends JpaRepository<OrderEntity, Long> {

}
