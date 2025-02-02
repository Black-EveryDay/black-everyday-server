package com.ed.payment.infrastructure.in.mq;

import static com.ed.payment.libs.common.constant.KafkaTopics.ORDER_PAYMENT_CANCEL_REQUEST;
import static com.ed.payment.libs.common.exception.ErrorCode.COMMON_SYSTEM_ERROR;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.payment.application.port.in.CancelPaymentUseCase;
import com.ed.payment.libs.common.exception.CustomException;
import java.io.IOException;
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

    try {
      cancelPaymentUseCase.cancelPayment(payload);
    } catch (IOException e) {
      throw new CustomException(COMMON_SYSTEM_ERROR);
    }
  }

  private void logConsumerRecord(OrderPaymentCancelRequestEvent request) {
    log.info("Received Request = userId: {}, paymentId: {}, requestDateTime: {}",
        request.getUserId(), request.getPaymentId(), request.getRequestDateTime());
  }
}