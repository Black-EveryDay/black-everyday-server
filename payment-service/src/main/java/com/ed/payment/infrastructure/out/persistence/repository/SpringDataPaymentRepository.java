package com.ed.payment.infrastructure.out.persistence.repository;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

interface SpringDataPaymentRepository extends JpaRepository<PaymentJpaEntity, Long> {

  boolean existsByOrderPublicId(String orderPublicId);

  Optional<PaymentJpaEntity> findByOrderPublicId(String orderPublicId);

  @Query("SELECT p FROM PaymentJpaEntity p " +
      "WHERE p.userPublicId = :userPublicId " +
      "AND p.paymentStatus NOT IN :paymentStatuses " +
      "AND p.confirmDeadline > :requestDateTime")
  List<PaymentJpaEntity> findMyReadyPayments(String userPublicId, List<PaymentStatus> paymentStatuses, LocalDateTime requestDateTime);

  @Modifying(clearAutomatically = true)
  @Query("UPDATE PaymentJpaEntity p SET p.paymentStatus = :paymentStatus WHERE p.id IN :ids")
  void bulkUpdatePaymentStatus(List<Long> ids, PaymentStatus paymentStatus);
}
