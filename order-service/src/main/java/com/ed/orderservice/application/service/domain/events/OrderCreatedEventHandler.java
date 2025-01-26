package com.ed.orderservice.application.service.domain.events;

import com.ed.orderservice.application.service.domain.order.create.OrderPaymentCreateService;
import com.ed.orderservice.domain.vo.order.event.OrderCreatedEvent;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedEventHandler {
  private final OrderPaymentCreateService orderPaymentCreateService;

  @Async("asyncExecutor")
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public CompletableFuture<Void> handleOrderCreatedEvent(OrderCreatedEvent event) {
    return CompletableFuture.runAsync(() -> {
      try {
        orderPaymentCreateService.requestPaymentConfirmation(event.getOrderEvent(), event.getOrderEventDto().getOrderPublicId());
      } catch (Exception e) {
        log.error("Failed to request payment confirmation for order: {}",
            event.getOrderEventDto().getOrderPublicId(), e);
      }
    }).exceptionally(ex -> {
      log.error("Unexpected error in handleOrderCreatedEvent", ex);
      return null;
    });
  }

}
