package com.ed.orderservice.application.service.serializer;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.orderservice.domain.vo.order.Order;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.stereotype.Component;

@Component
public class OrderPaymentRequestSerializer {

  public OrderPaymentCreateRequestEvent mapToOrderPaymentCreateRequest(Order order) {

    return OrderPaymentCreateRequestEvent.newBuilder()
        .setUserId(order.getUserId())
        .setOrderId(order.getOrderPublicId())
        .setOrderName(order.getOrderPublicName())
        .setMessageTimestamp(order.getOrderTimeLine().getOrderDate())
        .setPaymentDeadline(order.getOrderTimeLine().getPaymentDeadline())
        .setOrderCancelDeadline(order.getOrderTimeLine().getOrderCancelDeadline())
        .setTotalAmount(order.getTotalAmount())
        .build();
  }

  public byte[] serializeToByteArray(OrderPaymentCreateRequestEvent request) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
      DatumWriter<OrderPaymentCreateRequestEvent> writer = new SpecificDatumWriter<>(
          OrderPaymentCreateRequestEvent.class);
      writer.write(request, encoder);
      encoder.flush();
      return out.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Serialization failed", e);
    }
  }

  public OrderPaymentCreateRequestEvent deserializeFromByteArray(byte[] bytes) {
    try {
      BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
      DatumReader<OrderPaymentCreateRequestEvent> reader = new SpecificDatumReader<>(
          OrderPaymentCreateRequestEvent.class);
      return reader.read(null, decoder);
    } catch (IOException e) {
      throw new RuntimeException("Failed to deserialize OrderPaymentCreateRequestEvent", e);
    }
  }

}
