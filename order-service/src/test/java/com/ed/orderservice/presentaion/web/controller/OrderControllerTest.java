package com.ed.orderservice.presentaion.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.orderservice.application.port.in.command.CreateOrderCommand;
import com.ed.orderservice.application.port.in.dto.OrderItemDto;
import com.ed.orderservice.domain.vo.order.Order;
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

    @Test
    @DisplayName("성공")
    void createOrderSusscesTest() throws Exception {
      // Given
      NewOrderRequest request = NewOrderRequest.builder()
          .orderer(Orderer.builder()
              .name("홍길동")
              .phoneNumber("010-1234-5678")
              .build())
          .orderItemDtos(Arrays.asList(
              OrderItemDto.builder()
                  .brandId("나이키")
                  .productId("SHIRT001")
                  .productName("클래식 화이트 셔츠")
                  .quantity(2)
                  .unitPrice(59000L)
                  .size("M")
                  .productCategory(ProductCategory.TOP)
                  .build(),
              OrderItemDto.builder()
                  .brandId("아디다스")
                  .productId("JEANS002")
                  .productName("슬림핏 데님 진")
                  .quantity(1)
                  .unitPrice(79000L)
                  .size("32")
                  .productCategory(ProductCategory.BOTTOM)
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
              .build()).build();

      Order mockOrder = createMockOrder(request);
      when(createOrderUseCase.createOrder(any(CreateOrderCommand.class))).thenReturn(mockOrder);

      mockMvc.perform(post(uri)
              .contentType(MediaType.APPLICATION_JSON)
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
                              
                              ---
                              
                              - Body에는 주문 정보를 입력해주세요.
                              - 주문자 정보, 주문 상품 목록, 배송 정보를 포함해야 합니다.
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

    private Order createMockOrder(NewOrderRequest request) {
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

      return Order.builder()
          .orderId(1L)
          .orderer(request.getOrderer())
          .orderItems(orderItems)
          .orderDelivery(orderDelivery)
          .build();
    }
  }
}
