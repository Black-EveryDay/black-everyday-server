package com.ed.orderservice.presentaion.web.controller.internal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.orderservice.application.port.in.command.OrderSettlementCommand;
import com.ed.orderservice.domain.vo.order.settlement.CouponSettlement;
import com.ed.orderservice.domain.vo.order.settlement.OrderItemSettlement;
import com.ed.orderservice.domain.vo.order.settlement.OrderSettlement;
import com.ed.orderservice.presentaion.port.in.OrderSettlementUseCase;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // MockBean을 MockitoBean으로 변경
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderSettlementInternalController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderSettlementInternalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private OrderSettlementUseCase orderSettlementUseCase;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  @DisplayName("주문 정산 조회")
  class GetOrderSettlement {

    private static final String BASE_URL = "/api/v1/internal/orders";

    @Test
    @DisplayName("성공")
    void successGetOrderSettlement() throws Exception {
      // Given
      String orderId = "202501210172253716";
      OrderSettlement orderSettlement = createMockOrderSettlement();

      when(orderSettlementUseCase.getOrderSettlement(any(OrderSettlementCommand.class)))
          .thenReturn(orderSettlement);

      // When & Then
      mockMvc.perform(RestDocumentationRequestBuilders.get(BASE_URL + "/{orderId}", orderId)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcRestDocumentationWrapper.document("get-order-settlement",
              resource(ResourceSnippetParameters.builder()
                  .tag("주문 정산")
                  .summary("주문 정산 조회")
                  .description("""
                      ## 주문 정산 조회 엔드포인트입니다.
                        
                      - 주문 ID를 입력하시면
                      - 주문에 대한 상품과 쿠폰에 대한 정보를 확인 할 수 있습니다.
                      
                      """)
                  .pathParameters(
                      parameterWithName("orderId").description("주문 ID")
                  )
                  .responseFields(
                      fieldWithPath("success").description("요청 성공 여부"),
                      fieldWithPath("body").description("응답 본문"),
                      fieldWithPath("body.orderPublicId").description("주문 공개 ID"),
                      fieldWithPath("body.orderItems").description("주문 항목 목록"),
                      fieldWithPath("body.orderItems[].orderItemPublicId").description(
                          "주문 항목 공개 ID"),
                      fieldWithPath("body.orderItems[].brandPublicId").description("브랜드 공개 ID"),
                      fieldWithPath("body.orderItems[].productPublicId").description("상품 공개 ID"),
                      fieldWithPath("body.orderItems[].quantity").description("수량"),
                      fieldWithPath("body.orderItems[].unitPrice").description("단가"),
                      fieldWithPath("body.orderItems[].coupon").description("쿠폰 정보"),
                      fieldWithPath("body.orderItems[].coupon.couponTemplatePublicId").description(
                          "쿠폰 템플릿 공개 ID"),
                      fieldWithPath("body.orderItems[].coupon.orderCouponPublicId").description(
                          "주문 쿠폰 공개 ID"),
                      fieldWithPath("body.orderItems[].coupon.discountAmount").description("할인 금액"),
                      fieldWithPath("timestamp").description("응답 시간")
                  )
                  .build())
          ));
    }
  }

  private OrderSettlement createMockOrderSettlement() {
    return OrderSettlement.builder()
        .orderPublicId("202501210172253716")
        .orderItems(Arrays.asList(
            OrderItemSettlement.builder()
                .orderItemPublicId("2a3a8162-fcff-4887-8764-777c0dafb314")
                .brandPublicId("33732dd3-3901-4429-ba58-676c6965923")
                .productPublicId("e4030362-0251-441a-b752-63feea51337c")
                .quantity(1)
                .unitPrice(40000L)
                .coupon(CouponSettlement.builder()
                    .couponTemplatePublicId("a8de324d-3f9f-40a8-b62a-03fb3f47c40f")
                    .orderCouponPublicId("3a9f045f-d58c-4359-ba3e-8f52e2a2ea56")
                    .discountAmount(new BigDecimal("10000.00"))
                    .build())
                .build(),
            OrderItemSettlement.builder()
                .orderItemPublicId("f0e8e694-eabb-4721-b84e-691672c07520")
                .brandPublicId("55732dd3-3901-4429-ba58-676c6965923")
                .productPublicId("37eede74-184e-404e-a4f6-b1c063d91795")
                .quantity(1)
                .unitPrice(50000L)
                .coupon(CouponSettlement.builder()
                    .couponTemplatePublicId("a8de324d-3f9f-40a8-b62a-03fb3f47c40f")
                    .orderCouponPublicId("78964230-8be4-4211-9ada-234660f33ceb")
                    .discountAmount(new BigDecimal("10000.00"))
                    .build())
                .build()
        ))
        .build();
  }
}
