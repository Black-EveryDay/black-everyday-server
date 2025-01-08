package com.ed.payment.infrastructure.out.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {
  Optional<PaymentJpaEntity> findByOrderPublicId(String orderPublicId);
  boolean existsByOrderPublicId(String orderPublicId);
}
