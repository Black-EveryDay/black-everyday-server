package com.ed.payment.infrastructure.out.persistence.entity;

import static com.ed.payment.domain.PaymentStatus.READY;
import static jakarta.persistence.TemporalType.TIMESTAMP;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ed_payments")
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PaymentJpaEntity extends BaseJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long id;

  @Column(name = "payment_public_id", unique = true)
  private String paymentKey;

  @Column(unique = true)
  private String idempotencyKey;

  @Column(nullable = false)
  private String userPublicId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(nullable = false, unique = true)
  private String orderPublicId;

  @Column(nullable = false)
  private String orderName;

  @Column(nullable = false)
  private Long amount;

  @Column(nullable = false)
  private Long balance;

  @Temporal(TIMESTAMP)
  @Column(nullable = false)
  private LocalDateTime paymentDeadline;

  public static PaymentJpaEntity initPayment(
      String userPublicId, String orderPublicId, String orderName, Long amount,
      LocalDateTime paymentDeadline) {

    PaymentJpaEntity entity = PaymentJpaEntity.builder()
        .idempotencyKey(UUID.randomUUID().toString())
        .userPublicId(userPublicId)
        .paymentStatus(READY)
        .orderPublicId(orderPublicId)
        .orderName(orderName)
        .amount(amount)
        .balance(amount)
        .paymentDeadline(paymentDeadline)
        .build();
    entity.createBy(userPublicId);

    return entity;
  }

  public void updatePaymentStatus(PaymentStatus newStatus) {
    this.paymentStatus = newStatus;
  }

  public void updatePaymentAfterVerifying(PaymentStatus status, String paymentKey) {
    this.paymentKey = paymentKey;
    updatePaymentStatus(status);
  }
}
