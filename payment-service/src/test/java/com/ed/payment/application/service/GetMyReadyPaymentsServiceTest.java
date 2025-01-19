package com.ed.payment.application.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.PaymentResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMyReadyPaymentsServiceTest {

  @InjectMocks
  private GetMyReadyPaymentsService getReadyPaymentService;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Test
  @DisplayName("getMyReadyPayments: 결제 가능한 내 결제 리스트를 조회한다.")
  void getMyReadyPayments_success() {
    // given
    final String userPublicId = UUID.randomUUID().toString();

    PaymentResponse paymentResponse = createPaymentResponse();

    // stubbing
    when(getPaymentPort.getReadyPayments(userPublicId))
        .thenReturn(List.of(paymentResponse));

    // when
    getReadyPaymentService.getMyReadyPayments(userPublicId);

    // then
    verify(getPaymentPort).getReadyPayments(userPublicId);
  }

  private PaymentResponse createPaymentResponse() {
    return PaymentResponse.builder()
        .paymentPublicId(UUID.randomUUID().toString())
        .idempotencyKey(UUID.randomUUID().toString())
        .orderPublicId(UUID.randomUUID().toString())
        .orderName("피자맛 호빵")
        .amount(10000L)
        .confirmDeadline(LocalDateTime.now().plusDays(5))
        .cancelDeadLine(LocalDateTime.now().minusDays(5))
        .build();
  }
}