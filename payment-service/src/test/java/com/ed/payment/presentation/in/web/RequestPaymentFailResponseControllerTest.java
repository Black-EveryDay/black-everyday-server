package com.ed.payment.presentation.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.payment.application.port.in.command.HandleFailPaymentCommand;
import com.ed.payment.application.port.in.HandleFailPaymentUseCase;
import com.ed.payment.application.port.out.pg.dtos.PaymentFailResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@WebMvcTest(RequestPaymentFailController.class)
class RequestPaymentFailResponseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private HandleFailPaymentUseCase handleFailPaymentUseCase;

  @Test
  @DisplayName("confirmPayment: 결제 승인을 위한 정보를 받아 결제 승인을 요청한다.")
  void confirmPayment_ok() throws Exception {
    // given
    final String uri = "/api/v1/payments/fail";

    final String code = "FAILED_CARD_COMPANY";
    final String message = "카드사 점검 중으로 다른 카드를 이용해 주세요.";
    final String orderId = UUID.randomUUID().toString();

    MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
    queryParams.add("code", code);
    queryParams.add("message", message);
    queryParams.add("orderId", orderId);

    PaymentFailResponse response = PaymentFailResponse.of(code, message, orderId);

    // stubbing
    when(handleFailPaymentUseCase.handleFailPayment(any(HandleFailPaymentCommand.class)))
        .thenReturn(response);

    // expected
    mockMvc.perform(get(uri).queryParams(queryParams))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(Boolean.TRUE))
        .andExpect(jsonPath("$.body.code").value(response.getCode()))
        .andExpect(jsonPath("$.body.message").value(response.getMessage()))
        .andExpect(jsonPath("$.body.orderId").value(response.getOrderId()))
        .andExpect(jsonPath("$.timestamp").exists());
  }
}