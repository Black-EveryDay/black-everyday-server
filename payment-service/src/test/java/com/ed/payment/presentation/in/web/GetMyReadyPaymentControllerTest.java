package com.ed.payment.presentation.in.web;

import static com.ed.payment.libs.common.constant.CustomHttpHeaders.HEADER_USER_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.payment.application.port.in.GetMyReadyPaymentsUseCase;
import com.ed.payment.application.port.out.persistence.PaymentResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GetMyReadyPaymentController.class)
class GetMyReadyPaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GetMyReadyPaymentsUseCase getMyReadyPaymentsUseCase;

  @Test
  @DisplayName("getMyReadyPayments: 결제 가능한 내 결제 리스트를 조회한다.")
  void getMyReadyPayments() throws Exception {
    // given
    final String uri = "/api/v1/payments/me";

    final String userPublicId = UUID.randomUUID().toString();

    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderPublicId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final Long amount = 10000L;
    final LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    final LocalDateTime cancelDeadLine = LocalDateTime.now().plusDays(5);
    List<PaymentResponse> response = List.of(new PaymentResponse(
        paymentPublicId, idempotencyKey, orderPublicId, orderName, amount,
        confirmDeadline, cancelDeadLine));

    // stubbing
    when(getMyReadyPaymentsUseCase.getMyReadyPayments(userPublicId))
        .thenReturn(response);

    // expected
    mockMvc.perform(get(uri).header(HEADER_USER_ID, userPublicId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(Boolean.TRUE))
        .andExpect(jsonPath("$.body[0].paymentPublicId").value(response.getFirst().getPaymentPublicId()))
        .andExpect(jsonPath("$.body[0].idempotencyKey").value(response.getFirst().getIdempotencyKey()))
        .andExpect(jsonPath("$.body[0].orderPublicId").value(response.getFirst().getOrderPublicId()))
        .andExpect(jsonPath("$.body[0].orderName").value(response.getFirst().getOrderName()))
        .andExpect(jsonPath("$.body[0].amount").value(response.getFirst().getAmount()))
        .andExpect(jsonPath("$.body[0].confirmDeadline").exists())
        .andExpect(jsonPath("$.body[0].cancelDeadLine").exists())
        .andExpect(jsonPath("$.timestamp").exists());
  }
}