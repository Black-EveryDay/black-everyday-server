package com.ed.payment.infrastructure.out.persistence.repository;

import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_NOT_FOUND;

import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentPersistenceAdapter
    implements CreatePaymentPort, ReadPaymentPort, UpdatePaymentPort {

  private final SpringDataPaymentRepository paymentRepository;
  private final PaymentMapper paymentMapper;

  @Override
  public Payment initPayment(
      String userPublicId, String orderPublicId, String orderName, Long amount,
      LocalDateTime paymentDeadline) {
    return paymentMapper.mapToDomain(
        paymentRepository.save(
            PaymentJpaEntity.initPayment(
                userPublicId, orderPublicId, orderName, amount, paymentDeadline)));
  }

  @Override
  public Payment findPayment(String orderPublicId) {
    return paymentMapper.mapToDomain(
        paymentRepository.findByOrderPublicId(orderPublicId)
            .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND)));
  }

  @Override
  public Optional<PaymentJpaEntity> findOptPayment(String orderPublicId) {
    return paymentRepository.findByOrderPublicId(orderPublicId);
  }

  @Override
  public void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {
    getPaymentJpaEntity(paymentId).updatePaymentStatus(paymentStatus);
  }

  @Override
  public void updatePaymentAfterVerifying(
      Long paymentId, PaymentStatus paymentStatus, String paymentKey) {
    getPaymentJpaEntity(paymentId).updatePaymentAfterVerifying(paymentStatus, paymentKey);
  }

  private PaymentJpaEntity getPaymentJpaEntity(Long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
  }
}
