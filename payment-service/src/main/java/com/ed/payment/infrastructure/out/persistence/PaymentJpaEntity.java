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
public class PaymentJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "payment_id")
  private Long id;

  @Column(name = "payment_public_id", unique = true)
  private String paymentKey;

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
  private int amount;

  public static PaymentJpaEntity initPayment(
      String userPublicId, String orderPublicId, String orderName, int amount) {
    return PaymentJpaEntity.builder()
        .userPublicId(userPublicId)
        .paymentStatus(READY)
        .orderPublicId(orderPublicId)
        .orderName(orderName)
        .amount(amount)
        .build();
  }

  public void updatePaymentStatus(PaymentStatus newStatus) {
    this.paymentStatus = newStatus;
  }

  public void updatePaymentAfterVerifying(String paymentKey, PaymentStatus status) {
    this.paymentKey = paymentKey;
    updatePaymentStatus(status);
  }
}
