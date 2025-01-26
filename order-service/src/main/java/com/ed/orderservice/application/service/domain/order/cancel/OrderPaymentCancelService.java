package com.ed.orderservice.application.service.domain.order.cancel;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.orderservice.application.port.out.OrderPaymentCancelOutPort;
import com.ed.orderservice.application.service.domain.order.cancel.serializer.OrderPaymentCancelRequestSerializer;
import com.ed.orderservice.domain.vo.order.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderPaymentCancelService {

  private final OrderPaymentCancelRequestSerializer orderPaymentCancelRequestSerializer;
  private final OrderPaymentCancelOutPort orderPaymentCancelOutPort;

  public void requestPaymentCancel(OrderEvent orderEvent, String orderId) {
    orderPaymentCancelOutPort.sendPaymentCancelRequest(deserializeOrderPaymentRequest(orderEvent));

  }

  private OrderPaymentCancelRequestEvent deserializeOrderPaymentRequest(OrderEvent orderEvent){

    return orderPaymentCancelRequestSerializer.deserializeFromByteArray(orderEvent.getPlayLoad());
  }
}
