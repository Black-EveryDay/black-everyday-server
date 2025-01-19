package com.ed.payment.infrastructure.out.persistence.entity;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
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

  @Builder(builderMethodName = "createPaymentHistory", builderClassName = "CreatePaymentHistory")
  private PaymentHistoryJpaEntity(Long paymentId, Long amount) {
    this.paymentHistoryPublicId = generatePublicId();
    this.paymentId = paymentId;
    this.paymentStatus = READY;
    this.totalAmount = amount;
    this.balanceAmount = amount;
  }

  @Builder(builderMethodName = "createFailPaymentHistory", builderClassName = "CreateFailPaymentHistory")
  private PaymentHistoryJpaEntity(Long paymentId) {
    this.paymentHistoryPublicId = generatePublicId();
    this.paymentId = paymentId;
    this.paymentStatus = ABORTED;
  }

  @Builder(builderMethodName = "createConfirmPaymentHistory", builderClassName = "CreateConfirmPaymentHistory")
  private PaymentHistoryJpaEntity(
      Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus,
      Long totalAmount, Long balanceAmount) {
    this.paymentHistoryPublicId = generatePublicId();
    this.paymentId = paymentId;
    this.lastTransactionKey = lastTransactionKey;
    this.paymentStatus = paymentStatus;
    this.totalAmount = totalAmount;
    this.balanceAmount = balanceAmount;
  }

  @Builder(builderMethodName = "createCancelPaymentHistory", builderClassName = "CreateCancelPaymentHistory")
  private PaymentHistoryJpaEntity(
      Long paymentId, String lastTransactionKey, PaymentStatus paymentStatus,
      Long totalAmount, Long balanceAmount, Long cancelAmount, String cancelReason) {
    this.paymentHistoryPublicId = generatePublicId();
    this.paymentId = paymentId;
    this.lastTransactionKey = lastTransactionKey;
    this.paymentStatus = paymentStatus;
    this.totalAmount = totalAmount;
    this.balanceAmount = balanceAmount;
    this.cancelAmount = cancelAmount;
    this.cancelReason = cancelReason;
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
