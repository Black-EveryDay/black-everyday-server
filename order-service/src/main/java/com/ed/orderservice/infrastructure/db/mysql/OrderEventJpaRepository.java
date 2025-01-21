package com.ed.orderservice.infrastructure.db.mysql;

import com.ed.orderservice.infrastructure.entity.OrderEventEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventJpaRepository extends JpaRepository<OrderEventEntity, Long> {

  Optional<OrderEventEntity> findByOrderEntity_OrderPublicId(String orderPublicId);
}
