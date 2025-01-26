package com.ed.orderservice.presentaion.web.controller.internal;

import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
import java.util.List;
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

    private static final String BASE_URL = "/api/v1/internal/orders/settlements";

    @Test
    @DisplayName("성공")
    void successGetOrderSettlements() throws Exception {
      // Given
      List<String> orderIds = Arrays.asList(
          "202501220897635214",
          "202501220055539487"
      );

      List<OrderSettlement> orderSettlements = createMockOrderSettlements();

      when(orderSettlementUseCase.getOrderSettlements(any(OrderSettlementCommand.class)))
          .thenReturn(orderSettlements);

      // When & Then
      mockMvc.perform(RestDocumentationRequestBuilders.post(BASE_URL)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(orderIds)))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body", hasSize(2)))
          .andExpect(jsonPath("$.body[0].orderPublicId").value("202501220897635214"))
          .andExpect(jsonPath("$.body[1].orderPublicId").value("202501220055539487"))
          .andDo(MockMvcRestDocumentationWrapper.document("get-order-settlements",
              resource(ResourceSnippetParameters.builder()
                  .tag("주문 정산")
                  .summary("주문 정산 조회")
                  .description("""
                    ## 주문 정산 조회 엔드포인트입니다.
                      
                    - 주문 ID 리스트를 입력하시면
                    - 각 주문에 대한 상품과 쿠폰에 대한 정보를 확인할 수 있습니다.
                    
                    """)
                  .requestFields(
                      fieldWithPath("[]").description("주문 ID 리스트")
                  )
                  .responseFields(
                      fieldWithPath("success").description("요청 성공 여부"),
                      fieldWithPath("body").description("응답 본문"),
                      fieldWithPath("body[].orderPublicId").description("주문 공개 ID"),
                      fieldWithPath("body[].orderItems").description("주문 항목 목록"),
                      fieldWithPath("body[].orderItems[].orderItemPublicId").description("주문 항목 공개 ID"),
                      fieldWithPath("body[].orderItems[].brandPublicId").description("브랜드 공개 ID"),
                      fieldWithPath("body[].orderItems[].productPublicId").description("상품 공개 ID"),
                      fieldWithPath("body[].orderItems[].quantity").description("수량"),
                      fieldWithPath("body[].orderItems[].unitPrice").description("단가"),
                      fieldWithPath("body[].orderItems[].coupon").description("쿠폰 정보"),
                      fieldWithPath("body[].orderItems[].coupon.couponTemplatePublicId").description("쿠폰 템플릿 공개 ID"),
                      fieldWithPath("body[].orderItems[].coupon.orderCouponPublicId").description("주문 쿠폰 공개 ID"),
                      fieldWithPath("body[].orderItems[].coupon.discountAmount").description("할인 금액"),
                      fieldWithPath("timestamp").description("응답 시간")
                  )
                  .build())
          ));
    }
  }

  private List<OrderSettlement> createMockOrderSettlements() {
    return Arrays.asList(
        OrderSettlement.builder()
            .orderPublicId("202501220897635214")
            .orderItems(Arrays.asList(
                OrderItemSettlement.builder()
                    .orderItemPublicId("705dbb8e-5455-40a1-9a5d-be2d63946b48")
                    .brandPublicId("33732dd3-3901-4429-ba58-676c6965923")
                    .productPublicId("e4030362-0251-441a-b752-63feea51337c")
                    .quantity(1)
                    .unitPrice(40000L)
                    .coupon(CouponSettlement.builder()
                        .couponTemplatePublicId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .orderCouponPublicId("45718643-58c1-45da-bcfc-d39a6b084fca")
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build(),
                OrderItemSettlement.builder()
                    .orderItemPublicId("e32788e8-5c82-4abf-8890-9489197ab104")
                    .brandPublicId("55732dd3-3901-4429-ba58-676c6965923")
                    .productPublicId("37eede74-184e-404e-a4f6-b1c063d91795")
                    .quantity(3)
                    .unitPrice(50000L)
                    .coupon(CouponSettlement.builder()
                        .couponTemplatePublicId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .orderCouponPublicId("fd6d8e7f-9642-4db9-a1bf-fe7e3ee4c958")
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build()
            ))
            .build(),
        OrderSettlement.builder()
            .orderPublicId("202501220055539487")
            .orderItems(Arrays.asList(
                OrderItemSettlement.builder()
                    .orderItemPublicId("169d4950-1d36-4315-b62e-6d70aee2b69d")
                    .brandPublicId("33732dd3-3901-4429-ba58-676c6965923")
                    .productPublicId("e4030362-0251-441a-b752-63feea51337c")
                    .quantity(1)
                    .unitPrice(40000L)
                    .coupon(CouponSettlement.builder()
                        .couponTemplatePublicId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .orderCouponPublicId("45718643-58c1-45da-bcfc-d39a6b084fca")
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build(),
                OrderItemSettlement.builder()
                    .orderItemPublicId("74e114fd-f3ed-435f-abc1-1f09bf85ca9a")
                    .brandPublicId("55732dd3-3901-4429-ba58-676c6965923")
                    .productPublicId("37eede74-184e-404e-a4f6-b1c063d91795")
                    .quantity(3)
                    .unitPrice(50000L)
                    .coupon(CouponSettlement.builder()
                        .couponTemplatePublicId("8731dbc5-29e8-4e53-b078-a6e15ae676c1")
                        .orderCouponPublicId("fd6d8e7f-9642-4db9-a1bf-fe7e3ee4c958")
                        .discountAmount(new BigDecimal("10000.00"))
                        .build())
                    .build()
            ))
            .build()
    );
  }

}
