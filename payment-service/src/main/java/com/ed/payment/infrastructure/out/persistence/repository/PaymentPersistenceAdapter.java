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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentPersistenceAdapter
    implements CreatePaymentPort, ReadPaymentPort, UpdatePaymentPort {

  private final SpringDataPaymentRepository paymentRepository;
  private final PaymentMapper paymentMapper;

  @Override
  public Payment createPayment(
      String userPublicId, String orderPublicId, String orderName, Long amount,
      LocalDateTime confirmDeadline, LocalDateTime cancelDeadLine) {

    PaymentJpaEntity entity = PaymentJpaEntity.createPayment(
        userPublicId, orderPublicId, orderName, amount, confirmDeadline, cancelDeadLine);

    return paymentMapper.mapToDomain(paymentRepository.save(entity));
  }

  @Override
  public boolean existsByOrderPublicId(String orderPublicId) {
    return paymentRepository.existsByOrderPublicId(orderPublicId);
  }

  @Override
  public Payment getPaymentByOrderPublicId(String orderPublicId) {
    PaymentJpaEntity entity = paymentRepository.findByOrderPublicId(orderPublicId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));

    return paymentMapper.mapToDomain(entity);
  }

  @Override
  public void updatePaymentStatusById(Long paymentId, PaymentStatus paymentStatus) {
    getPaymentJpaEntity(paymentId).updatePaymentStatus(paymentStatus);
  }

  @Override
  public void updatePaymentStatusAndPaymentKeyById(Long paymentId, PaymentStatus paymentStatus, String paymentKey) {
    getPaymentJpaEntity(paymentId).updatePaymentStatusAndPaymentKey(paymentStatus, paymentKey);
  }

  private PaymentJpaEntity getPaymentJpaEntity(Long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
  }
}
