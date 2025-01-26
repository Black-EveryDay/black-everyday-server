package com.ed.orderservice.application.service.domain.order.cancel.serializer;

import com.ed.OrderPaymentCancelRequestEvent;
import com.ed.orderservice.domain.vo.order.Order;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
public class OrderPaymentCancelRequestSerializer {

  public OrderPaymentCancelRequestEvent mapToOrderPaymentCancelRequest(
      Order order,
      String cancelReason) {


    return OrderPaymentCancelRequestEvent.newBuilder()
        .setUserId(order.getUserId())
        .setOrderId(order.getOrderPublicId())
        .setPaymentId("23422-324")
        .setCancelAmount(order.getTotalAmount())
        .setCancelReason(cancelReason)
        .setRequestDateTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime())
        .build();
  }

  public byte[] serializeToByteArray(OrderPaymentCancelRequestEvent request) {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
      DatumWriter<OrderPaymentCancelRequestEvent> writer = new SpecificDatumWriter<>(
          OrderPaymentCancelRequestEvent.class);
      writer.write(request, encoder);
      encoder.flush();
      return out.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException("Serialization failed", e);
    }
  }

  public OrderPaymentCancelRequestEvent deserializeFromByteArray(byte[] bytes) {
    try {
      BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
      DatumReader<OrderPaymentCancelRequestEvent> reader = new SpecificDatumReader<>(
          OrderPaymentCancelRequestEvent.class);
      return reader.read(null, decoder);
    } catch (IOException e) {
      throw new RuntimeException("Failed to deserialize OrderPaymentCancelRequestEvent", e);
    }
  }
}
