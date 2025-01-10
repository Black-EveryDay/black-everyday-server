package com.ed.payment.infrastructure.out.mq;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_REQUEST;

import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.domain.Payment;
import com.ed.payment.infrastructure.out.mq.record.OrderPaymentRequest;
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
public class OrderPaymentConsumer {

  private final ReadPaymentPort readPaymentPort;
  private final CreatePaymentPort createPaymentPort;
  private final CreatePaymentHistoryPort createPaymentHistoryPort;

  @KafkaListener(topics = ORDER_PAYMENT_REQUEST)
  public void receiveOrderCreated(ConsumerRecord<String, OrderPaymentRequest> consumerRecord) {
    OrderPaymentRequest request = consumerRecord.value();
    log.info(
        "Received = userPublicId: {}, orderPublicId: {}, orderName: {}, amount: {}, paymentDeadline: {}, messageTimestamp: {}",
        request.getUserPublicId(),
        request.getOrderPublicId(),
        request.getOrderName(),
        request.getTotalAmount(),
        request.getPaymentDeadline(),
        request.getRequestDateTime());

    Optional<PaymentJpaEntity> optPaymentJpa = readPaymentPort.findOptPayment(request.getOrderPublicId());
    if (optPaymentJpa.isPresent() && isInvalidPaymentRequest(optPaymentJpa.get(), request)) {
      return;
    }

    initPaymentAndPaymentHistory(request);
  }

  private boolean isInvalidPaymentRequest(PaymentJpaEntity paymentJpaEntity, OrderPaymentRequest request) {
    if (isDuplicatedPaymentRequest(paymentJpaEntity)) {
      logDuplicatedPaymentRequest(request);
      return true;
    }

    if (isExpiredPaymentRequest(paymentJpaEntity, request)) {
      logExpiredPaymentRequest(paymentJpaEntity, request);
      return true;
    }

    return false;
  }

  private boolean isDuplicatedPaymentRequest(PaymentJpaEntity paymentEntity) {
    return paymentEntity.getPaymentStatus() == DONE;
  }

  private boolean isExpiredPaymentRequest(PaymentJpaEntity paymentEntity, OrderPaymentRequest request) {
    return paymentEntity.getPaymentDeadline().isBefore(request.getRequestDateTime());
  }

  private void logDuplicatedPaymentRequest(OrderPaymentRequest request) {
    log.info("Duplicated Order Payment Request = userPublicId: {}, orderPublicId: {}, requestDateTime: {}",
        request.getUserPublicId(),
        request.getOrderPublicId(),
        request.getRequestDateTime()
    );
  }

  private void logExpiredPaymentRequest(PaymentJpaEntity paymentJpaEntity, OrderPaymentRequest request) {
    log.info("Expired Order Payment Request = userPublicId: {}, orderPublicId: {}, paymentDeadline: {}, requestDateTime: {}",
        request.getUserPublicId(),
        request.getOrderPublicId(),
        paymentJpaEntity.getPaymentDeadline(),
        request.getRequestDateTime()
    );
  }

  private void initPaymentAndPaymentHistory(OrderPaymentRequest request) {
    Payment payment = createPaymentPort.initPayment(
        request.getUserPublicId(),
        request.getOrderPublicId(),
        request.getOrderName(),
        request.getTotalAmount(),
        request.getPaymentDeadline()
    );

    createPaymentHistoryPort.initPaymentHistory(
        payment.getPaymentId(),
        payment.getAmount()
    );
  }
}