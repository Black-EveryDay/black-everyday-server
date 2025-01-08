package com.ed.payment.infrastructure.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPaymentHistoryRepository
    extends JpaRepository<PaymentHistoryJpaEntity, Long> {
}
