package com.ed.orderservice.application.service.domain.order.create;


import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.application.port.out.OrderPaymentCreateOutPort;
import com.ed.orderservice.application.port.out.OrderStatusUpdateOutPort;
import com.ed.orderservice.application.service.domain.order.create.serializer.OrderPaymentRequestSerializer;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderPaymentCreateService {

  private final OrderPaymentCreateOutPort orderPaymentCreateOutPort;
  private final OrderStatusUpdateOutPort  orderStatusUpdateOutPort;
  private final OrderPaymentRequestSerializer orderPaymentRequestSerializer;

  public void requestPaymentConfirmation(OrderEvent orderEvent, String orderId) {
    orderStatusUpdateOutPort.updateToPaymentRequest(orderId);
    orderPaymentCreateOutPort.sendPaymentConfirmRequest(deserializeOrderPaymentRequest(orderEvent));
  }

  private OrderPaymentCreateRequestEvent deserializeOrderPaymentRequest(OrderEvent orderEvent){

    return orderPaymentRequestSerializer.deserializeFromByteArray(orderEvent.getPlayLoad());
  }

}
