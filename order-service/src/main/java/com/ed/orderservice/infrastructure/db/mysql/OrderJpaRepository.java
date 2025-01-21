package com.ed.orderservice.infrastructure.db.mysql;

import com.ed.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository
	extends JpaRepository<OrderEntity, Long> {

	OrderEntity findByOrderPublicId(String orderPublicId);
}
