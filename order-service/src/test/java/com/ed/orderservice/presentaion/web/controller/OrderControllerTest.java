package com.ed.orderservice.presentaion.web.controller;

import static com.ed.orderservice.domain.vo.order.OrderBase.generateOrderPublicId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
import com.ed.orderservice.domain.vo.order.OrderBase;
import com.ed.orderservice.domain.vo.order.OrderDelivery;
import com.ed.orderservice.domain.vo.order.Orderer;
import com.ed.orderservice.domain.vo.order.item.OrderItem;
import com.ed.orderservice.domain.vo.receiver.Receiver;
import com.ed.orderservice.domain.vo.receiver.ReceiverAddress;
import com.ed.orderservice.infrastructure.external.fegin.domain.product.dto.ProductCategory;
import com.ed.orderservice.presentaion.port.in.CreateOrderUseCase;
import com.ed.orderservice.presentaion.web.request.NewOrderRequest;
import com.ed.orderservice.presentaion.web.request.OrderDeliveryRequest;
import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.epages.restdocs.apispec.ResourceDocumentation;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CreateOrderUseCase createOrderUseCase;

  @Autowired
  private ObjectMapper objectMapper;

  @Nested
  @DisplayName("주문 생성")
  class createOrder {

    final String uri = "/api/v1/orders";
    final String userId = "550e8400-e29b-41d4-a716-446655440006";

    @Test
    @DisplayName("성공")
    void createOrderSusscesTest() throws Exception {
      // Given
      NewOrderRequest request = getNewOrderRequest();

      Order mockOrder = createMockOrder(request, userId);
      when(createOrderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(mockOrder);

      mockMvc.perform(post(uri)
              .contentType(MediaType.APPLICATION_JSON)
              .header("X-User-Id", userId)
              .content(objectMapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andDo(MockMvcRestDocumentationWrapper.document("create-order",
              preprocessRequest(prettyPrint()),
              preprocessResponse(prettyPrint()),
              ResourceDocumentation.resource(
                  ResourceSnippetParameters.builder()
                      .tag("ORDER-SERVICE V1")
                      .summary("주문 생성")
                      .description(
                          """
                              ## 주문 생성 엔드포인트입니다.
                              
                              - Body에는 주문 정보를 입력해주세요.
                              - 주문자 정보, 주문 상품 목록, 배송 정보를 포함해야 합니다.
                              
                              1. 상품 서비스, 재고 차감
                              2. 쿠폰 서비스, 쿠폰 검증
                              ㄴ 위 서비스에 상품의 재고와, 쿠폰이 먼저 존재 해야 생성이 간으합니다.
                              
                              3. 결제 서비스, 주문 결제 합니다.
                              
                              """)
                      .requestFields(
                          fieldWithPath("orderer.name").description("주문자 이름"),
                          fieldWithPath("orderer.phoneNumber").description("주문자 전화번호"),
                          fieldWithPath("orderItemDtos[].brandId").description("브랜드 ID"),
                          fieldWithPath("orderItemDtos[].productId").description("상품 ID"),
                          fieldWithPath("orderItemDtos[].productName").description("상품명"),
                          fieldWithPath("orderItemDtos[].quantity").description("수량"),
                          fieldWithPath("orderItemDtos[].unitPrice").description("단가"),
                          fieldWithPath("orderItemDtos[].size").description("사이즈"),
                          fieldWithPath("orderItemDtos[].productCategory").description("상품 카테고리"),
                          fieldWithPath("orderItemDtos[].orderItemCouponId").description(
                              "주문 상품 쿠폰 ID"),
                          fieldWithPath("orderDeliveryRequest.receiverName").description("수령인 이름"),
                          fieldWithPath("orderDeliveryRequest.receiverAddress").description(
                              "배송지 주소"),
                          fieldWithPath("orderDeliveryRequest.receiverPhoneNumber").description(
                              "수령인 전화번호"),
                          fieldWithPath("orderDeliveryRequest.receiverMobileNumber").description(
                              "수령인 휴대폰 번호"),
                          fieldWithPath("orderDeliveryRequest.zipcode").description("우편번호"),
                          fieldWithPath("orderDeliveryRequest.roadZipCode").description("도로명 우편번호"),
                          fieldWithPath("orderDeliveryRequest.requirement").description("배송 요청사항")
                      )
                      .requestHeaders(
                          headerWithName("X-User-Id").description("사용자 ID")
                      )
                      .responseFields(
                          fieldWithPath("success").description("성공 여부"),
                          fieldWithPath("body.orderItemDtos[]").description("주문 상품 목록"),
                          fieldWithPath("body.orderItemDtos[].brandId").description("브랜드 ID"),
                          fieldWithPath("body.orderItemDtos[].productId").description("상품 ID"),
                          fieldWithPath("body.orderItemDtos[].productName").description("상품명"),
                          fieldWithPath("body.orderItemDtos[].quantity").description("수량"),
                          fieldWithPath("body.orderItemDtos[].unitPrice").description("단가"),
                          fieldWithPath("body.orderItemDtos[].size").description("사이즈"),
                          fieldWithPath("body.orderItemDtos[].productCategory").description(
                              "상품 카테고리"),
                          fieldWithPath("body.orderItemDtos[].orderItemCouponId").description(
                              "주문 상품 쿠폰 ID"),
                          fieldWithPath("body.orderDeliveryInfo.receiverAddress").description(
                              "배송지 주소"),
                          fieldWithPath("body.orderDeliveryInfo.receiverName").description(
                              "수령인 이름"),
                          fieldWithPath("body.orderDeliveryInfo.receiverPhoneNumber").description(
                              "수령인 전화번호"),
                          fieldWithPath("body.orderDeliveryInfo.receiverMobileNumber").description(
                              "수령인 휴대폰 번호"),
                          fieldWithPath("body.orderDeliveryInfo.zipcode").description("우편번호"),
                          fieldWithPath("body.orderDeliveryInfo.roadZipCode").description(
                              "도로명 우편번호"),
                          fieldWithPath("body.orderDeliveryInfo.requirement").description(
                              "배송 요청사항"),
                          fieldWithPath("timestamp").description("응답 시간")
                      )
                      .build()
              )
          ));
    }

    private NewOrderRequest getNewOrderRequest() {
      return NewOrderRequest.builder()
          .orderer(Orderer.builder()
              .name("홍길동")
              .phoneNumber("010-1234-5678")
              .build())
          .orderItemDtos(Arrays.asList(
              OrderItemDto.builder()
                  .brandId("33732dd3-3901-4429-ba58-676c6965923")
                  .productId("e4030362-0251-441a-b752-63feea51337c")
                  .productName("베이직 긴팔 티셔츠")
                  .quantity(2)
                  .unitPrice(40000L)
                  .size("L")
                  .productCategory(ProductCategory.TOP)
                  .orderItemCouponId("3a9f045f-d58c-4359-ba3e-8f52e2a2ea56")
                  .build(),
              OrderItemDto.builder()
                  .brandId("55732dd3-3901-4429-ba58-676c6965923")
                  .productId("37eede74-184e-404e-a4f6-b1c063d91795")
                  .productName("스웻 팬츠")
                  .quantity(3)
                  .unitPrice(50000L)
                  .size("L")
                  .productCategory(ProductCategory.BOTTOM)
                  .orderItemCouponId("78964230-8be4-4211-9ada-234660f33ceb")
                  .build()
          ))
          .orderDeliveryRequest(OrderDeliveryRequest.builder()
              .receiverName("김철수")
              .receiverAddress("서울시 강남구 테헤란 123")
              .receiverPhoneNumber("02-907-6543")
              .receiverMobileNumber("010-9076-5432")
              .zipcode("06234")
              .roadZipCode("06234")
              .requirement("부재시 경비실에 맡겨주세요")
              .build())
          .build();

    }

    private Order createMockOrder(NewOrderRequest request, String userId) {
      List<OrderItem> orderItems = request.getOrderItemDtos().stream()
          .map(dto -> OrderItem.builder()
              .brandId(dto.getBrandId())
              .productId(dto.getProductId())
              .productName(dto.getProductName())
              .quantity(dto.getQuantity())
              .unitPrice(dto.getUnitPrice())
              .size(dto.getSize())
              .productCategory(dto.getProductCategory())
              .build())
          .toList();

      Receiver receiver = Receiver.builder()
          .name(request.getOrderDeliveryRequest().getReceiverName())
          .phoneNumber(request.getOrderDeliveryRequest().getReceiverPhoneNumber())
          .mobileNumber(request.getOrderDeliveryRequest().getReceiverMobileNumber())
          .requirement(request.getOrderDeliveryRequest().getRequirement())
          .build();

      ReceiverAddress receiverAddress = ReceiverAddress.builder()
          .address(request.getOrderDeliveryRequest().getReceiverAddress())
          .zipCode(request.getOrderDeliveryRequest().getZipcode())
          .roadZipCode(request.getOrderDeliveryRequest().getRoadZipCode())
          .build();

      OrderDelivery orderDelivery = OrderDelivery.builder()
          .receiver(receiver)
          .receiverAddress(receiverAddress)
          .build();

      OrderBase orderBase = OrderBase.builder()
          .orderPublicId(generateOrderPublicId())
          .orderPublicName("상품입력")
          .build();

      return Order.builder()
          .orderId(1L)
          .orderer(request.getOrderer())
          .orderItems(orderItems)
          .orderDelivery(orderDelivery)
          .userId(userId)
          .productTransactionId(UUID.randomUUID().toString())
          .orderBase(orderBase)
          .build();
    }

  }

}
