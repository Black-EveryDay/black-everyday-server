package com.ed.orderservice.presentaion.web.controller;

import static com.ed.orderservice.libs.common.HttpHeaderConstants.HEADER_USER_ID;

import com.ed.orderservice.application.port.in.command.CancelOrderCommand;
import com.ed.orderservice.domain.vo.order.OrderBase;
import com.ed.orderservice.domain.vo.order.OrderDelivery;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.order.item.OrderItemCoupon;
import com.ed.orderservice.domain.vo.order.item.OrderItemCouponTemplate;
import com.ed.orderservice.domain.vo.receiver.Receiver;
import com.ed.orderservice.domain.vo.receiver.ReceiverAddress;
import com.ed.orderservice.infrastructure.external.fegin.domain.coupon.enums.DiscountType;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.ProductCategory;
import com.ed.orderservice.presentaion.port.in.CancelOrderUseCase;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.orderservice.domain.vo.order.Order;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderCancelController.class)
@AutoConfigureRestDocs
class OrderCancelControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CancelOrderUseCase cancelOrderUseCase;

  @Test
  void cancelOrder_ShouldReturnOrderCancelResponse() throws Exception {
    // Given
    String orderId = "202501220897635214";
    String userUuid = "aff9c535-cd4c-4252-989b-7bb10d9aec3b";
    String cancelReason = "Test cancel reason";

    when(cancelOrderUseCase.cancelOrder(any(CancelOrderCommand.class)))
        .thenReturn(mockOrder());

    // When & Then
    mockMvc.perform(put("/api/v1/orders/{orderId}/cancel", orderId)
            .header(HEADER_USER_ID, userUuid)
            .param("cancelReason", cancelReason)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(MockMvcRestDocumentationWrapper.document("order-cancel",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            ResourceDocumentation.resource(
                ResourceSnippetParameters.builder()
                    .tag("ORDER-SERVICE V1")
                    .summary("주문 취소")
                    .description(
                        """
                            ## 주문 취소 엔드포인트입니다.
                            
                            - Path 파라미터로 취소할 주문의 ID를 입력해주세요.
                            - Query 파라미터로 취소 사유를 선택적으로 입력할 수 있습니다.
                            
                            1. 주문 상태 확인
                            2. 결제 취소 처리
                            3. 재고 복구
                            4. 사용된 쿠폰 복구
                            
                            주문 취소는 결제 완료 후 일정 시간 내에만 가능합니다.
                            """)
                    .pathParameters(
                        parameterWithName("orderId").description("취소할 주문 ID")
                    )
                    .queryParameters(
                        parameterWithName("cancelReason").description("취소 사유").optional()
                    )
                    .requestHeaders(
                        headerWithName(HEADER_USER_ID).description("사용자 ID")
                    )
                    .responseFields(
                        fieldWithPath("success").description("성공 여부"),
                        fieldWithPath("body.orderId").description("취소된 주문 ID"),
                        fieldWithPath("body.cancelAmount").description("취소 금액"),
                        fieldWithPath("timestamp").description("응답 시간")
                    )
                    .build()
            )
        ))
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.orderId").value(orderId))
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());
  }

  private Order mockOrder() {
    return Order.builder()
        .orderId(1L)
        .orderer(Orderer.builder()
            .name("홍길동")
            .phoneNumber("010-1234-5678")
            .build())
        .orderItems(Arrays.asList(
            OrderItem.builder()
                .brandId("33732dd3-3901-4429-ba58-676c6965923")
                .productId("e4030362-0251-441a-b752-63feea51337c")
                .productName("베이직 긴팔 티셔츠")
                .quantity(1)
                .unitPrice(40000L)
                .size("S")
                .productCategory(ProductCategory.TOP)
                .orderItemCoupon(OrderItemCoupon.builder()
                    .orderCouponId(1L)
                    .orderCouponPublicId(UUID.randomUUID().toString())
                    .orderItemCouponTemplate(OrderItemCouponTemplate.builder()
                        .couponTemplateId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .couponName("string")
                        .discountType(DiscountType.FIXED)
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build())
                .build(),
            OrderItem.builder()
                .brandId("55732dd3-3901-4429-ba58-676c6965923")
                .productId("37eede74-184e-404e-a4f6-b1c063d91795")
                .productName("스웻 팬츠")
                .quantity(3)
                .unitPrice(50000L)
                .size("S")
                .productCategory(ProductCategory.BOTTOM)
                .orderItemCoupon(OrderItemCoupon.builder()
                    .orderCouponId(2L)
                    .orderCouponPublicId(UUID.randomUUID().toString())
                    .orderItemCouponTemplate(OrderItemCouponTemplate.builder()
                        .couponTemplateId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .couponName("string")
                        .discountType(DiscountType.FIXED)
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build())
                .build()
        ))
        .orderDelivery(OrderDelivery.builder()
            .receiver(Receiver.builder()
                .name("김철수")
                .phoneNumber("02-907-6543")
                .mobileNumber("010-9076-5432")
                .requirement("부재시 경비실에 맡겨주세요")
                .build())
            .receiverAddress(ReceiverAddress.builder()
                .address("서울시 강남구 테헤란 123")
                .roadZipCode("06234")
                .build())
            .build())
        .userId("aff9c535-cd4c-4252-989b-7bb10d9aec3b")
        .productTransactionId("112")
        .orderBase(OrderBase.builder()
            .orderPublicId("202501220897635214")
            .orderPublicName("베이직 긴팔 티셔츠 외 1")
            .build())
        .build();

  }


}
