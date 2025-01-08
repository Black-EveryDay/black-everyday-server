package com.ed.payment.infrastructure.out.persistence;

import static com.ed.payment.libs.common.ErrorCode.PAYMENT_NOT_FOUND;

import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.libs.common.CustomException;
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
      String userPublicId, String orderPublicId, String orderName, int amount) {
    return paymentMapper.mapToDomain(
        paymentRepository.save(
            PaymentJpaEntity.initPayment(
                userPublicId, orderPublicId, orderName, amount)));
  }

  @Override
  public Payment findPayment(String orderPublicId) {
    return paymentMapper.mapToDomain(
        paymentRepository.findByOrderPublicId(orderPublicId)
            .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND)));
  }

  @Override
  public boolean existsPayment(String orderPublicId) {
    return paymentRepository.existsByOrderPublicId(orderPublicId);
  }

  @Override
  public void updatePaymentStatus(Long paymentId, PaymentStatus status) {
    getPaymentJpaEntity(paymentId).updatePaymentStatus(status);
  }

  @Override
  public void updatePaymentAfterVerifying(Long paymentId, String paymentKey, PaymentStatus status) {
    getPaymentJpaEntity(paymentId).updatePaymentAfterVerifying(paymentKey, status);
  }

  private PaymentJpaEntity getPaymentJpaEntity(Long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
  }
}
