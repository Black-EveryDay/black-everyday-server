package com.ed.eventservice.events.adapter.out.kafka.adaptor;

import com.ed.eventservice.events.adapter.out.kafka.dto.CreateCouponEvent;
import com.ed.eventservice.events.adapter.out.kafka.producer.CouponMessageProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CouponEventListener {

  private final CouponMessageProducer couponMessageProducer;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleCreateCouponEvent(CreateCouponEvent createCouponEvent) {
    this.couponMessageProducer.sendCouponCreateRequest(createCouponEvent);
  }

}
