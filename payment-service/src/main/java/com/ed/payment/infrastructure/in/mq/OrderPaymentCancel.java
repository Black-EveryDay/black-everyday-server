package com.ed.payment.infrastructure.in.mq;

import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CANCEL_REQUEST;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.payment.application.port.in.CancelPaymentUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentCancel {

  private final CancelPaymentUseCase cancelPaymentUseCase;

  @KafkaListener(topics = ORDER_PAYMENT_CANCEL_REQUEST)
  public void receiveOrderCanceled(ConsumerRecord<String, OrderPaymentCancelRequestEvent> consumerRecord) {
    OrderPaymentCancelRequestEvent payload = consumerRecord.value();
    logConsumerRecord(payload);
    cancelPaymentUseCase.cancelPayment(payload);
  }

  private void logConsumerRecord(OrderPaymentCancelRequestEvent request) {
    log.info("Received Request = userId: {}, paymentId: {}, requestDateTime: {}",
        request.getUserId(), request.getPaymentId(), request.getRequestDateTime());
  }
}