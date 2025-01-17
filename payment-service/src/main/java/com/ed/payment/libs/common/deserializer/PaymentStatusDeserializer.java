package com.ed.payment.libs.common.deserializer;

import com.ed.payment.domain.PaymentStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class PaymentStatusDeserializer extends JsonDeserializer<PaymentStatus> {

  @Override
  public PaymentStatus deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return switch (p.getValueAsString()) {
      case "READY" -> PaymentStatus.READY;
      case "DONE" -> PaymentStatus.DONE;
      case "CANCELED" -> PaymentStatus.CANCELED;
      case "PARTIAL_CANCELED" -> PaymentStatus.PARTIAL_CANCELED;
      default -> PaymentStatus.ABORTED;
    };
  }
}
