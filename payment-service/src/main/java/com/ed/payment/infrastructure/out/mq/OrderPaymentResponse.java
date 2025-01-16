package com.ed.payment.infrastructure.out.mq;

import com.ed.payment.application.port.out.mq.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentResponse<T> implements Producer<T> {

    private final KafkaTemplate<String, T> kafkaTemplate;

    @Override
    public boolean send(String topic, T payload) {
        log.info("Sending payload={} to topic={}", payload, topic);
        kafkaTemplate.send(topic, payload);
        return true;
    }
}
