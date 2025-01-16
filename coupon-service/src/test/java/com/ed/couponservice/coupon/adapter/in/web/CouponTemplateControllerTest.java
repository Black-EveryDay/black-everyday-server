package com.ed.couponservice.coupon.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.couponservice.coupon.adapter.in.web.dto.CreateCouponRequest;
import com.ed.couponservice.coupon.adapter.in.web.dto.CreateCouponTemplateRequest;
import com.ed.couponservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.couponservice.coupon.application.port.in.CreateCouponTemplateCommand;
import com.ed.couponservice.coupon.application.port.out.dto.CreateCouponTemplateResponse;
import com.ed.couponservice.coupon.domain.enums.CouponIssuanceType;
import com.ed.couponservice.coupon.domain.enums.CouponIssuerType;
import com.ed.couponservice.coupon.domain.enums.CouponUsageTargetType;
import com.ed.couponservice.coupon.domain.enums.DiscountType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(CouponTemplateController.class)
class CouponTemplateControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @MockitoBean
  private CouponTemplateUseCase couponTemplateUseCase;

  @Nested
  @DisplayName("CreateCouponTemplateTest")
  class CreateCouponTemplateTest {

    private final String uri = "/api/v1/coupon-templates";

    @Test
    @DisplayName("Should create coupon template success when request with minimum info")
    void shouldCreateCouponTemplateSuccessWhenRequestWithMinimumInfo() throws Exception {
      // given
      CreateCouponTemplateRequest createCouponTemplateRequest = CreateCouponTemplateRequest.builder()
          .couponName("couponName")
          .couponIssuanceType(CouponIssuanceType.AUTOMATIC)
          .couponIssuerType(CouponIssuerType.SERVICE)
          .couponUsageTargetType(CouponUsageTargetType.ALL)
          .discountType(DiscountType.PERCENTAGE)
          .discountValue(BigDecimal.valueOf(10.5))
          .build();

      CreateCouponTemplateResponse createCouponTemplateResponse = CreateCouponTemplateResponse.builder()
          .id(1L)
          .publicId(UUID.randomUUID())
          .couponName("couponName")
          .couponIssuanceType(CouponIssuanceType.AUTOMATIC)
          .couponIssuerType(CouponIssuerType.SERVICE)
          .couponIssuerId(null)
          .maxIssuance(null)
          .isIssuable(false)
          .couponUsageTargetType(CouponUsageTargetType.ALL)
          .couponUsageTargetId(null)
          .discountType(DiscountType.PERCENTAGE)
          .discountValue(BigDecimal.valueOf(10.5))
          .expirationDays(null)
          .expirationDate(null)
          .build();

      given(couponTemplateUseCase.createCouponTemplate(any(CreateCouponTemplateCommand.class)))
          .willReturn(createCouponTemplateResponse);
      // when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createCouponTemplateRequest)));

      // then
      resultActions.andExpect(status().isCreated())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.id").value(createCouponTemplateResponse.getId()))
          .andExpect(jsonPath("$.body.publicId").isNotEmpty())
          .andExpect(
              jsonPath("$.body.couponName").value(createCouponTemplateResponse.getCouponName()))
          .andExpect(jsonPath("$.body.couponIssuanceType").value(
              createCouponTemplateResponse.getCouponIssuanceType().toString()))
          .andExpect(jsonPath("$.body.couponIssuerType").value(
              createCouponTemplateResponse.getCouponIssuerType().toString()))
          .andExpect(jsonPath("$.body.couponIssuerId").isEmpty())
          .andExpect(jsonPath("$.body.maxIssuance").isEmpty())
          .andExpect(
              jsonPath("$.body.isIssuable").value(createCouponTemplateResponse.getIsIssuable()))
          .andExpect(jsonPath("$.body.couponUsageTargetType").value(
              createCouponTemplateResponse.getCouponUsageTargetType().toString()))
          .andExpect(jsonPath("$.body.couponUsageTargetId").isEmpty())
          .andExpect(
              jsonPath("$.body.discountType").value(
                  createCouponTemplateResponse.getDiscountType().toString()))
          .andExpect(jsonPath("$.body.discountValue").value(
              createCouponTemplateResponse.getDiscountValue()))
          .andExpect(jsonPath("$.body.expirationDays").isEmpty())
          .andExpect(jsonPath("$.body.expirationDate").isEmpty());

      verify(couponTemplateUseCase, times(1)).createCouponTemplate(
          any(CreateCouponTemplateCommand.class));
    }

    @ParameterizedTest
    @CsvSource({
        ", AUTOMATIC, SERVICE, ALL, PERCENTAGE,10.5",
        "couponName, , SERVICE, ALL, PERCENTAGE,10.5",
        "couponName, AUTOMATIC, , ALL, PERCENTAGE,10.5",
        "couponName, AUTOMATIC, SERVICE, , PERCENTAGE,10.5",
        "couponName, AUTOMATIC, SERVICE, ALL, ,10.5",
        "couponName, AUTOMATIC, SERVICE, ALL, PERCENTAGE,",
    })
    @DisplayName("Should create coupon template fail when required field is missing")
    void shouldCreateCouponTemplateSuccessWhenRequestWithMinimumInfo(String couponName,
        String couponIssuanceType, String couponIssuerType, String couponUsageTargetType,
        String discountType, BigDecimal discountValue) throws Exception {
      // given
      CreateCouponTemplateRequest createCouponTemplateRequest = CreateCouponTemplateRequest.builder()
          .couponName(couponName)
          .couponIssuanceType(
              couponIssuanceType == null ? null : CouponIssuanceType.valueOf(couponIssuanceType))
          .couponIssuerType(
              couponIssuerType == null ? null : CouponIssuerType.valueOf(couponIssuerType))
          .couponUsageTargetType(couponUsageTargetType == null ? null
              : CouponUsageTargetType.valueOf(couponUsageTargetType))
          .discountType(discountType == null ? null : DiscountType.valueOf(discountType))
          .discountValue(discountValue)
          .build();

      // when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createCouponTemplateRequest)));

      // then
      resultActions.andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.success").value(false));
    }
  }

  @Nested
  @DisplayName("CreateCouponTest")
  class createCouponTest {

    private final String couponTemplateId = UUID.randomUUID().toString();
    private final String uri = "/api/v1/coupon-templates/" + couponTemplateId + "/coupons";

    @Test
    @DisplayName("Should create coupon success")
    void shouldCreateCouponSuccess() throws Exception {
      //given
      CreateCouponRequest createCouponRequest = CreateCouponRequest.builder()
          .quantity(10)
          .build();

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(createCouponRequest)));

      //then
      resultActions.andExpect(status().isCreated())
          .andExpect(jsonPath("$.success").value(true));
    }

  }

}