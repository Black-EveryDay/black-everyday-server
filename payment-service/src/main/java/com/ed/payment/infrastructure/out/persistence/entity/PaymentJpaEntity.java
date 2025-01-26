package com.ed.payment.infrastructure.out.persistence.entity;

import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.READY;
import static com.ed.payment.domain.PaymentStatus.getCancelPaymentStatus;
import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.libs.common.entity.BaseTimeJpaEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "ED_PAYMENT")
@NoArgsConstructor(access = PROTECTED)
public class PaymentJpaEntity extends BaseTimeJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "PAYMENT_ID")
  private Long id;

  @Column(name = "PAYMENT_PUBLIC_ID" , unique = true)
  private String paymentPublicId;

  @Column(name = "PAYMENT_KEY", unique = true)
  private String paymentKey;

  @Column(name = "IDEMPOTENCY_KEY")
  private String idempotencyKey;

  @Column(name = "USER_PUBLIC_ID", nullable = false)
  private String userPublicId;

  @Column(name = "PAYMENT_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private PaymentStatus paymentStatus;

  @Column(name = "ORDER_PUBLIC_ID", nullable = false, unique = true)
  private String orderPublicId;

  @Column(name = "ORDER_NAME", nullable = false)
  private String orderName;

  @Column(name = "TOTAL_AMOUNT", nullable = false)
  private Long totalAmount;

  @Column(name = "BALANCE_AMOUNT", nullable = false)
  private Long balanceAmount;

  @Column(name = "CONFIRM_DEADLINE", nullable = false)
  private LocalDateTime confirmDeadline;

  @Column(name = "CANCEL_DEADLINE", nullable = false)
  private LocalDateTime cancelDeadLine;

  @OneToMany(mappedBy = "paymentJpaEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PaymentHistoryJpaEntity> paymentHistoryJpaEntities = new ArrayList<>();

  @Builder(builderMethodName = "createPayment", builderClassName = "CreatePayment")
  private PaymentJpaEntity(
      String userPublicId, String orderPublicId, String orderName,
      Long amount, LocalDateTime confirmDeadline, LocalDateTime cancelDeadLine) {
    this.paymentPublicId = generatePublicId();
    this.idempotencyKey = generatePublicId();
    this.userPublicId = userPublicId;
    this.paymentStatus = READY;
    this.orderPublicId = orderPublicId;
    this.orderName = orderName;
    this.totalAmount = amount;
    this.balanceAmount = amount;
    this.confirmDeadline = confirmDeadline;
    this.cancelDeadLine = cancelDeadLine;
  }

  public void addPaymentHistoryJpaEntity() {
    this.paymentHistoryJpaEntities.add(PaymentHistoryJpaEntity.createPaymentHistory(this));
  }

  public void confirm(PaymentStatus paymentStatus, String paymentKey) {
    this.paymentStatus = paymentStatus;
    this.paymentKey = paymentKey;
    addPaymentHistoryJpaEntity();
  }

  public void fail() {
    this.paymentStatus = ABORTED;
    addPaymentHistoryJpaEntity();
  }

  public void cancel(PaymentStatus paymentStatus, Long balanceAmount, Long cancelAmount, String cancelReason) {
    this.totalAmount -= cancelAmount;
    this.idempotencyKey = generatePublicId();
    this.paymentStatus = getCancelPaymentStatus(paymentStatus, this.totalAmount);
    this.balanceAmount = balanceAmount;
    this.paymentHistoryJpaEntities.add(
        PaymentHistoryJpaEntity.createCancelPaymentHistory(this, cancelAmount, cancelReason));
  }

  private static String generatePublicId() {
    return UUID.randomUUID().toString();
  }
}
