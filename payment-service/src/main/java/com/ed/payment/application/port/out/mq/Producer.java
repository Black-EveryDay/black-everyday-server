package com.ed.payment.application.port.out.mq;

public interface Producer<T> {
    boolean send(String topic, T payload);
}
