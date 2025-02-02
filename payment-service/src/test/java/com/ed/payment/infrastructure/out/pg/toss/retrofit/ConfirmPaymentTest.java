package com.ed.payment.infrastructure.out.pg.toss.retrofit;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.payment.infrastructure.out.pg.toss.dtos.TossPaymentDoneResponse;
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
class ConfirmPaymentTest {

  @InjectMocks
  private ConfirmPayment confirmPayment;
  @Mock
  private TossPaymentClient tossPaymentClient;
  @Mock
  private IdempotencyKeyInterceptor idempotencyKeyInterceptor;
  @Mock
  private PaymentPgMapper paymentPgMapper;

  @Test
  @DisplayName("confirmPayment: 결제 정보를 입력 받아 Tosspayments 의 결제 승인 외부 API 를 요청한다.")
  void confirmPayment() throws Exception {
  	// given
    final String idempotencyKey = UUID.randomUUID().toString();
    TossPaymentDoneResponse response = createTossPaymentDoneResponse();

    // stubbing
    when(tossPaymentClient.confirmPayment(any()))
        .thenReturn(Calls.response(response));

    // when
    confirmPayment.confirmPayment(idempotencyKey, any());

  	// then
    verify(tossPaymentClient).confirmPayment(any());
  }

  private TossPaymentDoneResponse createTossPaymentDoneResponse() {
    return TossPaymentDoneResponse.builder()
        .paymentKey("tgen_20250107154634hYNt7")
        .orderId(UUID.randomUUID().toString())
        .totalAmount(10000L)
        .balanceAmount(10000L)
        .status(DONE)
        .build();
  }
}