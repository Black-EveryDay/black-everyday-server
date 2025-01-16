package com.ed.couponservice.coupon.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.couponservice.coupon.adapter.in.web.dto.IssueCouponRequest;
import com.ed.couponservice.coupon.application.port.in.CouponUseCase;
import com.ed.couponservice.coupon.application.port.in.IssueCouponCommand;
import com.ed.couponservice.coupon.application.port.out.dto.IssueCouponResponse;
import com.ed.couponservice.coupon.domain.enums.CouponState;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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

@WebMvcTest(CouponController.class)
class CouponControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private JpaMetamodelMappingContext jpaMetamodelMappingContext;

  @MockitoBean
  private CouponUseCase couponUseCase;

  @Nested
  @DisplayName("issueCouponToUser method")
  class IssueCouponToUser {

    private final UUID couponId = UUID.randomUUID();
    private final String uri = "/api/v1/coupons/" + couponId + "/user";

    @Test
    @DisplayName("should issue coupon to user success")
    void shouldIssueCouponToUserSuccess() throws Exception {
      //given
      IssueCouponRequest request = IssueCouponRequest.builder()
          .userId(UUID.randomUUID())
          .build();

      IssueCouponResponse response = IssueCouponResponse.builder()
          .couponId(couponId)
          .expirationDate(LocalDateTime.now())
          .issuedAt(LocalDateTime.now())
          .userId(request.getUserId())
          .state(CouponState.ISSUED)
          .couponTemplate(
              IssueCouponResponse.CouponTemplateResponse.builder()
                  .templateId(UUID.randomUUID())
                  .couponName("couponName")
                  .build())
          .build();

      given(couponUseCase.issueCoupon(any(IssueCouponCommand.class)))
          .willReturn(response);

      //when
      ResultActions resultActions = mockMvc.perform(post(uri)
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(request)));

      //then
      resultActions.andExpect(status().isOk())
          .andExpect(jsonPath("$.success").value(true))
          .andExpect(jsonPath("$.body.couponId").value(couponId.toString()))
          .andExpect(jsonPath("$.body.userId").value(request.getUserId().toString()))
          .andExpect(jsonPath("$.body.state").value(CouponState.ISSUED.name()))
          .andExpect(jsonPath("$.body.couponTemplate.templateId").value(
              response.getCouponTemplate().getTemplateId().toString()))
          .andExpect(jsonPath("$.body.couponTemplate.couponName").value(
              response.getCouponTemplate().getCouponName()));
    }
  }

}