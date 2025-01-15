package com.ed.payment.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.PaymentResponse;
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

    final String paymentPublicId = UUID.randomUUID().toString();
    final String idempotencyKey = UUID.randomUUID().toString();
    final String orderPublicId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final Long amount = 10000L;
    final LocalDateTime confirmDeadline = LocalDateTime.now().plusDays(5);
    final LocalDateTime cancelDeadLine = LocalDateTime.now().minusDays(5);
    List<PaymentResponse> response = List.of(PaymentResponse.of(
        paymentPublicId, idempotencyKey, orderPublicId, orderName, amount,
        confirmDeadline, cancelDeadLine));

    // when
    when(getPaymentPort.getReadyPayments(userPublicId))
        .thenReturn(response);

    List<PaymentResponse> result = getReadyPaymentService.getMyReadyPayments(userPublicId);

    // then
    assertThat(result).isEqualTo(response);
    verify(getPaymentPort).getReadyPayments(userPublicId);
  }
}