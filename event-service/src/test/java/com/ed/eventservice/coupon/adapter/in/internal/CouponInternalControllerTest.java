package com.ed.eventservice.coupon.adapter.in.internal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.eventservice.coupon.adapter.in.internal.dto.UseCouponRequest;
import com.ed.eventservice.coupon.application.port.in.CouponCancelUseCommand;
import com.ed.eventservice.coupon.application.port.in.CouponUseCase;
import com.ed.eventservice.coupon.application.port.in.CouponUseCommand;
import com.ed.eventservice.coupon.application.port.out.dto.UseCouponResponse;
import com.ed.eventservice.coupon.application.port.out.dto.UseCouponResponse.CouponTemplateResponse;
import com.ed.eventservice.coupon.domain.enums.DiscountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CouponInternalController.class)
class CouponInternalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @MockitoBean
  private CouponUseCase couponUseCase;

  @Nested
  @DisplayName("UseCouponTest")
  class UseCouponTest {

    private final UUID couponId = UUID.randomUUID();
    private final String uri = "/api/v1/internal/coupons/" + couponId + "/orderInfo";

    @Test
    @DisplayName("Should use coupon success")
    void ShouldUseCouponSuccess() throws Exception {
      //given
      UseCouponRequest useCouponRequest = UseCouponRequest.builder()
          .userId(UUID.randomUUID())
          .productId(UUID.randomUUID())
          .brandId(UUID.randomUUID())
          .orderId(UUID.randomUUID())
          .build();

      UseCouponResponse useCouponResponse = UseCouponResponse.builder()
          .couponId(couponId)
          .couponTemplate(CouponTemplateResponse.builder()
              .templateId(UUID.randomUUID())
              .couponName("couponName")
              .discountType(DiscountType.PERCENTAGE)
              .discountValue(BigDecimal.valueOf(10.5))
              .build())
          .build();

      given(couponUseCase.useCoupon(any(CouponUseCommand.class))).willReturn(useCouponResponse);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(useCouponRequest)));

      //then
      resultActions.andExpect(status().isOk())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.couponId").value(couponId.toString()))
          .andExpect(jsonPath("$.body.couponTemplate.templateId").isNotEmpty())
          .andExpect(jsonPath("$.body.couponTemplate.couponName").isNotEmpty())
          .andExpect(
              jsonPath("$.body.couponTemplate.discountType").value(DiscountType.PERCENTAGE.name()))
          .andExpect(jsonPath("$.body.couponTemplate.discountValue").value(10.5));
    }
  }

  @Nested
  @DisplayName("CancelUseCouponTest")
  class CancelUseCouponTest {

    private final UUID couponId = UUID.randomUUID();
    private final String uri = "/api/v1/internal/coupons/" + couponId + "/orderInfo/cancel";

    @Test
    @DisplayName("Should cancel use coupon success")
    void ShouldCancelUseCouponSuccess() throws Exception {
      //given
      UseCouponResponse useCouponResponse = UseCouponResponse.builder()
          .couponId(couponId)
          .couponTemplate(CouponTemplateResponse.builder()
              .templateId(UUID.randomUUID())
              .couponName("couponName")
              .discountType(DiscountType.PERCENTAGE)
              .discountValue(BigDecimal.valueOf(10.5))
              .build())
          .build();

      given(couponUseCase.cancelUseCoupon(any(CouponCancelUseCommand.class))).willReturn(
          useCouponResponse);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON));

      //then
      resultActions.andExpect(status().isOk())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.couponId").value(couponId.toString()))
          .andExpect(jsonPath("$.body.couponTemplate.templateId").isNotEmpty())
          .andExpect(jsonPath("$.body.couponTemplate.couponName").isNotEmpty())
          .andExpect(
              jsonPath("$.body.couponTemplate.discountType").value(DiscountType.PERCENTAGE.name()))
          .andExpect(jsonPath("$.body.couponTemplate.discountValue").value(10.5));
    }
  }
}