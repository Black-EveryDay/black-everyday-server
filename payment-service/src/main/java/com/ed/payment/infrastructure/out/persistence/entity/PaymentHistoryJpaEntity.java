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

  @Column(name = "PAYMENT_HISTORY_PUBLIC_ID", nullable = false, unique = true)
  private String paymentHistoryPublicId;

  @Column(name = "PAYMENT_ID", nullable = false)
  private Long paymentId;

  @Column(name = "LAST_TRANSACTION_KEY")
  private String lastTransactionKey;

  @Column(name = "PAYMENT_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "TOTAL_AMOUNT")
  private Long totalAmount;

  @Column(name = "BALANCE_AMOUNT")
  private Long balanceAmount;

  @Column(name = "CANCEL_AMOUNT")
  private Long cancelAmount;

  @Column(name = "CANCEL_REASON")
  private String cancelReason;

  public static PaymentHistoryJpaEntity createPaymentHistory(Long paymentId, Long totalAmount, Long balanceAmount) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .paymentStatus(READY)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
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

  public static PaymentHistoryJpaEntity createCancelSuccessPaymentHistory(
      Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus,
      Long totalAmount, Long balanceAmount, Long cancelAmount, String cancelReason) {
    return PaymentHistoryJpaEntity.builder()
        .paymentHistoryPublicId(generatePublicId())
        .paymentId(paymentId)
        .lastTransactionKey(lastTransactionKey)
        .paymentStatus(paymentStatus)
        .totalAmount(totalAmount)
        .balanceAmount(balanceAmount)
        .cancelAmount(cancelAmount)
        .cancelReason(cancelReason)
        .build();
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
