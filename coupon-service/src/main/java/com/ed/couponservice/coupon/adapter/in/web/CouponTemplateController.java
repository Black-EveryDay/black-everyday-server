package com.ed.couponservice.coupon.adapter.in.web;

import static com.ed.couponservice.libs.common.ApiResponseUtils.created;

import com.ed.couponservice.coupon.adapter.in.web.dto.CreateCouponRequest;
import com.ed.couponservice.coupon.adapter.in.web.dto.CreateCouponTemplateRequest;
import com.ed.couponservice.coupon.adapter.in.web.dto.SearchCouponTemplatesCondition;
import com.ed.couponservice.coupon.application.port.in.CouponTemplateUseCase;
import com.ed.couponservice.coupon.application.port.in.command.CreateCouponCommand;
import com.ed.couponservice.coupon.application.port.in.command.SearchCouponTemplatesCommand;
import com.ed.couponservice.coupon.application.port.in.command.mapper.CommandMapper;
import com.ed.couponservice.coupon.application.port.out.dto.CouponTemplateDetailResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon-templates")
@RequiredArgsConstructor
public class CouponTemplateController {

  private final CouponTemplateUseCase couponTemplateUseCase;
  private final CommandMapper commandMapper;

  @PostMapping
  public ResponseEntity<CouponTemplateDetailResponse> createCouponTemplate(
      @Valid @RequestBody CreateCouponTemplateRequest createCouponTemplateRequest
  ) {
    return created(
        couponTemplateUseCase.createCouponTemplate(createCouponTemplateRequest.toCommand()));
  }

  @PostMapping("{couponTemplateId}/coupons")
  public ResponseEntity<String> createCoupon(
      @PathVariable UUID couponTemplateId,
      @Valid @RequestBody CreateCouponRequest createCouponRequest
  ) {
    couponTemplateUseCase.createCoupon(CreateCouponCommand.builder()
        .couponTemplateId(couponTemplateId)
        .quantity(createCouponRequest.getQuantity())
        .build());
    return created(null);
  }

  @GetMapping
  public PagedModel<CouponTemplateDetailResponse> searchCouponTemplates(
      SearchCouponTemplatesCondition searchCouponTemplatesCondition,
      @PageableDefault Pageable pageable
  ) {

    SearchCouponTemplatesCommand searchCouponTemplateCommand =
        commandMapper.searchCouponTemplateRequestToSearchCouponTemplateCommand(
            searchCouponTemplatesCondition,
            pageable
        );

    Page<CouponTemplateDetailResponse> couponTemplates =
        couponTemplateUseCase.searchCouponTemplates(searchCouponTemplateCommand);

    return new PagedModel<>(couponTemplates);
  }

}
