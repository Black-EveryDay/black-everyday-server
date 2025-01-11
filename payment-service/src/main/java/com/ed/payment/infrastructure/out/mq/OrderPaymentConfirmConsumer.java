package com.ed.payment.infrastructure.out.mq;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_REQUEST;

import com.ed.OrderPaymentConfirmRequest;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.domain.PaymentStatus;
import com.ed.payment.infrastructure.out.persistence.entity.PaymentJpaEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentConfirmConsumer {

  private final ReadPaymentPort readPaymentPort;
  private final CreatePaymentPort createPaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;

  @KafkaListener(topics = ORDER_PAYMENT_CONFIRM_REQUEST)
  public void receiveOrderCreated(ConsumerRecord<String, OrderPaymentConfirmRequest> consumerRecord) {
    OrderPaymentConfirmRequest request = consumerRecord.value();
    logConsumerRecord(request);

    Optional<PaymentJpaEntity> optPaymentJpa = readPaymentPort.findOptPaymentByOrderPublicId(request.getOrderId());
    if (optPaymentJpa.isPresent() && isInvalidPaymentRequest(optPaymentJpa.get(), request)) {
      return;
    }

    createPaymentAndPaymentHistory(request);
  }

  private boolean isInvalidPaymentRequest(PaymentJpaEntity paymentJpaEntity, OrderPaymentConfirmRequest request) {
    if (isBadPaymentRequest(paymentJpaEntity)) {
      logBadPaymentRequest(request, paymentJpaEntity);
      return true;
    }

    if (isExpiredPaymentRequest(request, paymentJpaEntity)) {
      logExpiredPaymentRequest(request, paymentJpaEntity);
      return true;
    }

    return false;
  }

  private void createPaymentAndPaymentHistory(OrderPaymentConfirmRequest request) {
    Payment payment = createPaymentPort.createPayment(
        request.getUserId(),
        request.getOrderId(),
        request.getOrderName(),
        request.getTotalAmount(),
        request.getPaymentDeadline(),
        request.getOrderCancelDeadline());

    createPaymentHistoryPort.createPaymentHistory(
        payment.getPaymentId(),
        payment.getAmount());
  }

  private boolean isBadPaymentRequest(PaymentJpaEntity paymentJpaEntity) {
    PaymentStatus paymentStatus = paymentJpaEntity.getPaymentStatus();
    return paymentStatus == DONE || paymentStatus == CANCELED;
  }

  private boolean isExpiredPaymentRequest(OrderPaymentConfirmRequest request, PaymentJpaEntity paymentJpaEntity) {
    return paymentJpaEntity.getConfirmDeadline().isBefore(request.getRequestDateTime());
  }

  private void logConsumerRecord(OrderPaymentConfirmRequest request) {
    log.info("Received Request = userId: {}, orderId: {}, orderName: {}, requestDateTime: {}, paymentDeadline: {}, orderCancelDeadline: {}, totalAmount: {}",
        request.getUserId(),
        request.getOrderId(),
        request.getOrderName(),
        request.getRequestDateTime(),
        request.getPaymentDeadline(),
        request.getOrderCancelDeadline(),
        request.getTotalAmount());
  }

  private void logBadPaymentRequest(OrderPaymentConfirmRequest request, PaymentJpaEntity paymentJpaEntity) {
    log.info("Bad Request = userId: {}, orderId: {}, requestDateTime: {}, paymentStatus: {}",
        request.getUserId(),
        request.getOrderId(),
        request.getRequestDateTime(),
        paymentJpaEntity.getPaymentStatus());
  }

  private void logExpiredPaymentRequest(OrderPaymentConfirmRequest request, PaymentJpaEntity paymentJpaEntity) {
    log.info("Expired Request = userId: {}, orderId: {}, requestDateTime: {}, confirmDeadline: {}",
        request.getUserId(),
        request.getOrderId(),
        request.getRequestDateTime(),
        paymentJpaEntity.getConfirmDeadline());
  }
}