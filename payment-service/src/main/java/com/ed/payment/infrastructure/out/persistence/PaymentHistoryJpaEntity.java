package com.ed.payment.infrastructure.out.persistence;

import static com.ed.payment.infrastructure.out.persistence.PaymentStatus.READY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ed_payment_histories")
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PaymentHistoryJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_history_id")
  private Long id;

  @Column(nullable = false, unique = true)
  private String paymentHistoryPublicId;

  @Column(nullable = false)
  private Long paymentId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column
  private int totalAmount;

  @Column
  private int balanceAmount;

  @Column
  private int cancelAmount;

  public static PaymentHistoryJpaEntity initPaymentHistory(
      Long paymentId, int amount) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .paymentStatus(READY)
        .totalAmount(amount)
        .balanceAmount(amount)
        .cancelAmount(0)
        .build();
  }

  public static PaymentHistoryJpaEntity createPaymentHistory(
      Long paymentId, int amount, PaymentStatus paymentStatus) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .paymentStatus(paymentStatus)
        .totalAmount(amount)
        .balanceAmount(amount)
        .cancelAmount(0)
        .build();
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
