package com.ed.payment.infrastructure.out.persistence.entity;

import static com.ed.payment.domain.PaymentStatus.READY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.entity.BaseTimeJpaEntity;
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
@Table(name = "ED_PAYMENT_HISTORY")
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class PaymentHistoryJpaEntity extends BaseTimeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PAYMENT_HISTORY_ID")
  private Long id;

  @Column(nullable = false, unique = true)
  private String paymentHistoryPublicId;

  @Column(nullable = false)
  private Long paymentId;

  @Column
  private String lastTransactionKey;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column
  private Long totalAmount;

  @Column
  private Long balanceAmount;

  @Column
  private Long cancelAmount;

  public static PaymentHistoryJpaEntity createPaymentHistory(Long paymentId, Long amount) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .paymentStatus(READY)
        .totalAmount(amount)
        .balanceAmount(amount)
        .build();
  }

  public static PaymentHistoryJpaEntity createConfirmSuccessPaymentHistory(
      Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus,
      Long totalAmount, Long balanceAmount) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .lastTransactionKey(lastTransactionKey)
        .paymentStatus(paymentStatus)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .build();
  }

  public static PaymentHistoryJpaEntity createFailPaymentHistory(
      Long paymentId, PaymentStatus paymentStatus) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .paymentStatus(paymentStatus)
        .build();
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
