package com.ed.payment.infrastructure.out.persistence.entity;

import static com.ed.payment.domain.PaymentStatus.READY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.entity.BaseTimeByJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_PAYMENT")
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PaymentJpaEntity extends BaseTimeByJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PAYMENT_ID")
  private Long id;

  @Column(unique = true)
  private String paymentPublicId;

  @Column(unique = true)
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
  private LocalDateTime confirmDeadline;

  @Column(nullable = false)
  private LocalDateTime cancelDeadLine;

  public static PaymentJpaEntity createPayment(
      String userPublicId, String orderPublicId, String orderName, Long amount,
      LocalDateTime confirmDeadline, LocalDateTime cancelDeadLine) {

    PaymentJpaEntity entity = PaymentJpaEntity.builder()
        .paymentPublicId(generatePublicId())
        .idempotencyKey(generatePublicId())
        .userPublicId(userPublicId)
        .paymentStatus(READY)
        .orderPublicId(orderPublicId)
        .orderName(orderName)
        .amount(amount)
        .confirmDeadline(confirmDeadline)
        .cancelDeadLine(cancelDeadLine)
        .build();
    entity.createBy(userPublicId);

    return entity;
  }

  public void updatePaymentStatus(PaymentStatus newPaymentStatus) {
    this.paymentStatus = newPaymentStatus;
  }

  public void updatePaymentStatusAndPaymentKey(PaymentStatus paymentStatus, String paymentKey) {
    this.paymentKey = paymentKey;
    updatePaymentStatus(paymentStatus);
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
