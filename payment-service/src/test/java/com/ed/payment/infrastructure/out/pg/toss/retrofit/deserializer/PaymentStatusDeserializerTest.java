package com.ed.payment.infrastructure.out.pg.toss.retrofit.deserializer;


import static com.ed.payment.domain.PaymentStatus.ABORTED;
import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.assertj.core.api.Assertions.assertThat;

import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentCanceledResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PaymentStatusDeserializerTest {

  private ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @DisplayName("paymentStatusDeserializer: PaymentStatus 에 정의된 status 외의 값은 ABORTED 로 변환한다.")
  void paymentStatusDeserializer() throws Exception {
  	// given
    String json1 = "{ \"paymentKey\": \"123\", \"orderId\": \"A-001\", \"totalAmount\": 5000, \"balanceAmount\": 2000, \"status\": \"DONE\" }";
    String json2 = "{ \"paymentKey\": \"456\", \"orderId\": \"A-002\", \"totalAmount\": 5000, \"balanceAmount\": 2000, \"status\": \"CANCELED\" }";
    String json3 = "{ \"paymentKey\": \"789\", \"orderId\": \"A-003\", \"totalAmount\": 5000, \"balanceAmount\": 2000, \"status\": \"UNKNOWN\" }";

  	// when
    TossPaymentCanceledResponse done = objectMapper.readValue(json1, TossPaymentCanceledResponse.class);
    TossPaymentCanceledResponse canceled = objectMapper.readValue(json2, TossPaymentCanceledResponse.class);
    TossPaymentCanceledResponse unknown = objectMapper.readValue(json3, TossPaymentCanceledResponse.class);

    // then
    assertThat(done.getStatus()).isEqualTo(DONE);
    assertThat(canceled.getStatus()).isEqualTo(CANCELED);
    assertThat(unknown.getStatus()).isEqualTo(ABORTED);
  }
}