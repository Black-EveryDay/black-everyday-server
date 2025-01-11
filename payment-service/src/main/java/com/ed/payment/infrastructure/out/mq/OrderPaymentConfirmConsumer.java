package com.ed.payment.infrastructure.out.mq;

import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CONFIRM_REQUEST;

import com.ed.OrderPaymentConfirmRequest;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.ReadPaymentPort;
import com.ed.payment.domain.Payment;
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

    if (readPaymentPort.existsByOrderPublicId(request.getOrderId())) {
      logBadPaymentRequest(request);
      return;
    }

    createPaymentAndPaymentHistory(request);
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

  private void logConsumerRecord(OrderPaymentConfirmRequest request) {
    log.info("Received Request = userId: {}, orderId: {}, orderName: {}, paymentDeadline: {}, orderCancelDeadline: {}, totalAmount: {}, messageTimestamp: {}",
        request.getUserId(),
        request.getOrderId(),
        request.getOrderName(),
        request.getPaymentDeadline(),
        request.getOrderCancelDeadline(),
        request.getTotalAmount(),
        request.getMessageTimestamp());
  }

  private void logBadPaymentRequest(OrderPaymentConfirmRequest request) {
    log.info("Bad Request = payment based on userId: {}, orderId: {} is already exist",
        request.getUserId(), request.getOrderId());
  }
}