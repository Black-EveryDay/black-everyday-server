package com.ed.payment.presentation.in.web;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.payment.application.port.in.ConfirmPaymentUseCase;
import com.ed.payment.application.port.in.command.ConfirmPaymentCommand;
import com.ed.payment.application.port.out.pg.dtos.PaymentDoneResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(ConfirmPaymentController.class)
class ConfirmPaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private ConfirmPaymentUseCase confirmPaymentUseCase;

  @Test
  @DisplayName("requestPaymentSuccess: 결제 승인을 위한 정보를 받아 결제 승인을 요청한다.")
  void requestPaymentSuccess_ok() throws Exception {
    // given
    final String uri = "/api/v1/payments/success";

    final String paymentType = "NORMAL";
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String orderId = UUID.randomUUID().toString();
    final Long amount = 10000L;

    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("paymentType", paymentType);
    queryParams.add("paymentKey", paymentKey);
    queryParams.add("orderId", orderId);
    queryParams.add("amount", String.valueOf(amount));

    PaymentDoneResponse response = PaymentDoneResponse.builder()
        .paymentKey(paymentKey)
        .orderId(orderId)
        .totalAmount(amount)
        .balanceAmount(amount)
        .paymentStatus(DONE)
        .build();

    // stubbing
    when(confirmPaymentUseCase.confirmPayment(any(ConfirmPaymentCommand.class)))
        .thenReturn(response);

    // expected
    mockMvc.perform(get(uri).queryParams(queryParams))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(Boolean.TRUE))
        .andExpect(jsonPath("$.body.paymentKey").value(response.getPaymentKey()))
        .andExpect(jsonPath("$.body.orderId").value(response.getOrderId()))
        .andExpect(jsonPath("$.body.totalAmount").value(response.getTotalAmount()))
        .andExpect(jsonPath("$.body.balanceAmount").value(response.getBalanceAmount()))
        .andExpect(jsonPath("$.body.paymentStatus").value(response.getPaymentStatus().toString()))
        .andExpect(jsonPath("$.timestamp").exists());
  }
}