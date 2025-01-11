package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
  boolean existsByOrderPublicId(String orderPublicId);
  Optional<PaymentJpaEntity> findByOrderPublicId(String orderPublicId);
}
