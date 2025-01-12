package com.ed.payment.infrastructure.in.mq;

import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CREATE_REQUEST;

import com.ed.OrderPaymentCreateRequest;
import com.ed.payment.application.port.in.CreatePaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentCreate {

  private final CreatePaymentUseCase createPaymentUseCase;

  @KafkaListener(topics = ORDER_PAYMENT_CREATE_REQUEST)
  public void receiveOrderCreated(ConsumerRecord<String, OrderPaymentCreateRequest> consumerRecord) {
    OrderPaymentCreateRequest request = consumerRecord.value();
    logConsumerRecord(request);
    createPaymentUseCase.createPayment(request);
  }

  private void logConsumerRecord(OrderPaymentCreateRequest request) {
    log.info("Received Request = userId: {}, orderId: {}, orderName: {}, paymentDeadline: {}, orderCancelDeadline: {}, totalAmount: {}, messageTimestamp: {}",
        request.getUserId(),
        request.getOrderId(),
        request.getOrderName(),
        request.getPaymentDeadline(),
        request.getOrderCancelDeadline(),
        request.getTotalAmount(),
        request.getMessageTimestamp());
  }
}