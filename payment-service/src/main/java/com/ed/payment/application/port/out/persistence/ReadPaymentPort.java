package com.ed.payment.application.port.out.persistence;

import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import java.util.Optional;

public interface ReadPaymentPort {
  Payment findPayment(String orderPublicId);
  Optional<PaymentJpaEntity> findOptPayment(String orderPublicId);
}
