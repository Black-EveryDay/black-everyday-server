package com.ed.payment.infrastructure.out.pg.toss.retrofit;

import static com.ed.payment.domain.PaymentStatus.CANCELED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentCanceledResponse;
import com.ed.payment.infrastructure.out.pg.toss.retrofit.interceptor.IdempotencyKeyInterceptor;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.mock.Calls;

@ExtendWith(MockitoExtension.class)
class CancelPaymentTest {

  @InjectMocks
  private CancelPayment cancelPayment;
  @Mock
  private TossPaymentClient tossPaymentClient;
  @Mock
  private IdempotencyKeyInterceptor idempotencyKeyInterceptor;
  @Mock
  private PaymentPgMapper paymentPgMapper;

  @Test
  @DisplayName("cancelPayment: 결제 취소 정보를 입력 받아 Tosspayments 의 결제 취소 외부 API 를 요청한다.")
  void cancelPayment() throws Exception {
  	// given
    final String paymentKey = "tgen_20250107154634hYNt7";
    final String idempotencyKey = UUID.randomUUID().toString();
    TossPaymentCanceledResponse response = createTossPaymentCanceledResponse(paymentKey);

    // stubbing
    when(tossPaymentClient.cancelPayment(any(), any()))
        .thenReturn(Calls.response(response));

    // when
    cancelPayment.cancelPayment(paymentKey, idempotencyKey, any());

  	// then
    verify(tossPaymentClient).cancelPayment(any(), any());
  }

  private TossPaymentCanceledResponse createTossPaymentCanceledResponse(String paymentKey) {
    return TossPaymentCanceledResponse.builder()
        .paymentKey(paymentKey)
        .orderId(UUID.randomUUID().toString())
        .totalAmount(10000L)
        .balanceAmount(10000L)
        .status(CANCELED)
        .cancels(null)
        .build();
  }
}