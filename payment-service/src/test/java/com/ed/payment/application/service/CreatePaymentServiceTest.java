package com.ed.payment.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentCreateRequest;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreatePaymentServiceTest {

  @InjectMocks
  private CreatePaymentService createPaymentService;

  @Mock
  private GetPaymentPort getPaymentPort;

  @Mock
  private CreatePaymentPort createPaymentPort;

  @Mock
  private CreatePaymentHistoryPort createPaymentHistoryPort;

  @Test
  @DisplayName("createPayment: 주문 생성 시 발행한 주문 메시지를 기반으로 결제 데이터를 생성한다.")
  void createPayment_success() {
  	// given
    final String userId = UUID.randomUUID().toString();
    final String orderId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final LocalDateTime paymentDeadline = LocalDateTime.now().plusDays(5);
    final LocalDateTime orderCancelDeadline = LocalDateTime.now().plusDays(5);
    final long totalAmount = 1000L;
    final LocalDateTime messageTimestamp = LocalDateTime.now();

    OrderPaymentCreateRequest orderPaymentCreateRequest = OrderPaymentCreateRequest.newBuilder()
        .setUserId(userId)
        .setOrderId(orderId)
        .setOrderName(orderName)
        .setPaymentDeadline(paymentDeadline)
        .setOrderCancelDeadline(orderCancelDeadline)
        .setTotalAmount(totalAmount)
        .setMessageTimestamp(messageTimestamp)
        .build();

    // stubbing
    when(getPaymentPort.existsByOrderPublicId(orderId))
        .thenReturn(false);

    when(createPaymentPort.createPayment(anyString(), anyString(), anyString(), anyLong(), any(), any()))
        .thenReturn(mock());

    doNothing().when(createPaymentHistoryPort)
        .createPaymentHistory(anyLong(), anyLong(), anyLong());

    // when
    createPaymentService.createPayment(orderPaymentCreateRequest);

  	// then
    verify(getPaymentPort).existsByOrderPublicId(orderId);
    verify(createPaymentPort).createPayment(anyString(), anyString(), anyString(), anyLong(), any(), any());
    verify(createPaymentHistoryPort).createPaymentHistory(anyLong(), anyLong(), anyLong());
  }

  @Test
  @DisplayName("createPayment: 같은 주문 아이디로 생성된 결제 데이터가 존재하면 실패한다.")
  void createPayment_fail_duplicated_order_payment() {
    // given
    final String userId = UUID.randomUUID().toString();
    final String orderId = UUID.randomUUID().toString();
    final String orderName = "피자맛 호빵";
    final LocalDateTime paymentDeadline = LocalDateTime.now().plusDays(5);
    final LocalDateTime orderCancelDeadline = LocalDateTime.now().plusDays(5);
    final long totalAmount = 1000L;
    final LocalDateTime messageTimestamp = LocalDateTime.now();

    OrderPaymentCreateRequest orderPaymentCreateRequest = OrderPaymentCreateRequest.newBuilder()
        .setUserId(userId)
        .setOrderId(orderId)
        .setOrderName(orderName)
        .setPaymentDeadline(paymentDeadline)
        .setOrderCancelDeadline(orderCancelDeadline)
        .setTotalAmount(totalAmount)
        .setMessageTimestamp(messageTimestamp)
        .build();

    // stubbing
    when(getPaymentPort.existsByOrderPublicId(orderId))
        .thenReturn(true);

    // when
    createPaymentService.createPayment(orderPaymentCreateRequest);

    // then
    verify(getPaymentPort).existsByOrderPublicId(orderId);
    verify(createPaymentPort, never()).createPayment(anyString(), anyString(), anyString(), anyLong(), any(), any());
    verify(createPaymentHistoryPort, never()).createPaymentHistory(anyLong(), anyLong(), anyLong());
  }
}