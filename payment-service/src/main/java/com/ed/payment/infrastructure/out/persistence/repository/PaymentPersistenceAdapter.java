package com.ed.payment.infrastructure.out.persistence.repository;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.libs.common.exception.ErrorCode.PAYMENT_NOT_FOUND;

import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.CreatePaymentRequest;
import com.ed.payment.application.port.out.persistence.dtos.PaymentResponse;
import com.ed.payment.application.port.out.persistence.UpdatePaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.UpdateCancelPaymentRequest;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import com.ed.payment.libs.common.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentPersistenceAdapter implements CreatePaymentPort, GetPaymentPort, UpdatePaymentPort {

  private final PaymentPersistenceMapper paymentPersistenceMapper;
  private final SpringDataPaymentRepository paymentRepository;

  @Override
  public Payment createPayment(CreatePaymentRequest request) {
    PaymentJpaEntity entity = paymentPersistenceMapper.createRequestToJpaEntity(request);
    return paymentPersistenceMapper.mapToDomain(paymentRepository.save(entity));
  }

  @Override
  public boolean existsByOrderPublicId(String orderPublicId) {
    return paymentRepository.existsByOrderPublicId(orderPublicId);
  }
  
  @Override
  public List<PaymentResponse> getReadyPayments(String userPublicId) {
    List<PaymentJpaEntity> entities = paymentRepository.findMyReadyPayments(userPublicId, List.of(DONE, CANCELED), LocalDateTime.now());
    return entities.stream()
        .map(paymentPersistenceMapper::mapToApplication)
        .toList();
  }

  @Override
  public Payment getPaymentByOrderPublicId(String orderPublicId) {
    PaymentJpaEntity entity = paymentRepository.findByOrderPublicId(orderPublicId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
    return paymentPersistenceMapper.mapToDomain(entity);
  }

  @Override
  public void updatePaymentStatusAbortedById(Long paymentId) {
    getPaymentJpaEntity(paymentId).fail();
  }

  @Override
  public void updatePaymentStatusAndPaymentKeyById(Long paymentId, PaymentStatus paymentStatus, String paymentKey) {
    getPaymentJpaEntity(paymentId).confirm(paymentStatus, paymentKey);
  }

  @Override
  public void updatePaymentStatusAndIdempotencyKeyById(UpdateCancelPaymentRequest request) {
    getPaymentJpaEntity(request.getPaymentId())
        .cancel(request.getPaymentStatus(), request.getBalanceAmount(), request.getCancelAmount(), request.getCancelReason());
  }

  private PaymentJpaEntity getPaymentJpaEntity(Long paymentId) {
    return paymentRepository.findById(paymentId)
        .orElseThrow(() -> new CustomException(PAYMENT_NOT_FOUND));
  }
}
