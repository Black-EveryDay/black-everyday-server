package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.infrastructure.out.persistence.entity.PaymentHistoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPaymentHistoryRepository
    extends JpaRepository<PaymentHistoryJpaEntity, Long> {
}
