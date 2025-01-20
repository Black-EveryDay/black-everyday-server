package com.ed.payment.infrastructure.out.persistence.entity;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.entity.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_PAYMENT_HISTORY")
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PaymentHistoryJpaEntity extends BaseTimeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PAYMENT_HISTORY_ID")
  private Long id;

  @Column(name = "PAYMENT_HISTORY_PUBLIC_ID", nullable = false, unique = true)
  private String paymentHistoryPublicId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAYMENT_ID", nullable = false)
  private PaymentJpaEntity paymentJpaEntity;

  @Column(name = "PAYMENT_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "CANCEL_AMOUNT")
  private Long cancelAmount;

  @Column(name = "CANCEL_REASON")
  private String cancelReason;

  public static PaymentHistoryJpaEntity createPaymentHistory(PaymentJpaEntity paymentJpaEntity) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentJpaEntity(paymentJpaEntity)
        .paymentStatus(paymentJpaEntity.getPaymentStatus())
        .build();
  }

  public static PaymentHistoryJpaEntity createCancelPaymentHistory(
      PaymentJpaEntity paymentJpaEntity, Long cancelAmount, String cancelReason) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentJpaEntity(paymentJpaEntity)
        .paymentStatus(paymentJpaEntity.getPaymentStatus())
        .cancelAmount(cancelAmount)
        .cancelReason(cancelReason)
        .build();
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}