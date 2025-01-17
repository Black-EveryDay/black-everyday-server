package com.ed.payment.application.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ed.OrderPaymentCreateRequestEvent;
import com.ed.payment.application.port.out.persistence.CreatePaymentHistoryPort;
import com.ed.payment.application.port.out.persistence.CreatePaymentPort;
import com.ed.payment.application.port.out.persistence.GetPaymentPort;
import com.ed.payment.application.port.out.persistence.dtos.CreatePaymentRequest;
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
  private OutPortPersistenceMapper outPortPersistenceMapper;

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
    final String orderId = UUID.randomUUID().toString();

    OrderPaymentCreateRequestEvent createOrderPaymentRequest = createCreateOrderPaymentRequest(orderId);
    CreatePaymentRequest createPaymentRequest = outPortPersistenceMapper.createPaymentToPersistence(createOrderPaymentRequest);

    // stubbing
    when(getPaymentPort.existsByOrderPublicId(orderId))
        .thenReturn(false);

    when(createPaymentPort.createPayment(createPaymentRequest))
        .thenReturn(mock());

    doNothing().when(createPaymentHistoryPort)
        .createPaymentHistory(anyLong(), anyLong());

    // when
    createPaymentService.createPayment(createOrderPaymentRequest);

  	// then
    verify(getPaymentPort).existsByOrderPublicId(orderId);
    verify(createPaymentPort).createPayment(createPaymentRequest);
    verify(createPaymentHistoryPort).createPaymentHistory(anyLong(), anyLong());
  }

  @Test
  @DisplayName("createPayment: 같은 주문 아이디로 생성된 결제 데이터가 존재하면 실패한다.")
  void createPayment_fail_duplicated_order_payment() {
    // given
    final String orderId = UUID.randomUUID().toString();

    OrderPaymentCreateRequestEvent createOrderPaymentRequest = createCreateOrderPaymentRequest(orderId);
    CreatePaymentRequest createPaymentRequest = outPortPersistenceMapper.createPaymentToPersistence(createOrderPaymentRequest);

    // stubbing
    when(getPaymentPort.existsByOrderPublicId(orderId))
        .thenReturn(true);

    // when
    createPaymentService.createPayment(createOrderPaymentRequest);

    // then
    verify(getPaymentPort).existsByOrderPublicId(orderId);
    verify(createPaymentPort, never()).createPayment(createPaymentRequest);
    verify(createPaymentHistoryPort, never()).createPaymentHistory(anyLong(), anyLong());
  }

  private OrderPaymentCreateRequestEvent createCreateOrderPaymentRequest(String orderId) {
    return OrderPaymentCreateRequestEvent.newBuilder()
        .setUserId(UUID.randomUUID().toString())
        .setOrderId(orderId)
        .setOrderName("피자맛 호빵")
        .setPaymentDeadline(LocalDateTime.now().plusDays(5))
        .setOrderCancelDeadline(LocalDateTime.now().plusDays(5))
        .setTotalAmount(1000L)
        .setMessageTimestamp(LocalDateTime.now())
        .build();
  }
}