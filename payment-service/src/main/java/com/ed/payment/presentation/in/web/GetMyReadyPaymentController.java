package com.ed.payment.presentation.in.web;

import static com.ed.payment.libs.common.constant.CustomHttpHeaders.HEADER_USER_ID;
import static com.ed.payment.libs.common.response.ApiResponseUtils.ok;

import com.ed.payment.application.port.in.GetMyReadyPaymentsUseCase;
import com.ed.payment.application.port.out.persistence.PaymentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GetMyReadyPaymentController {

  private final GetMyReadyPaymentsUseCase getMyReadyPaymentsUseCase;

  @GetMapping("/api/v1/payments/me")
  public ResponseEntity<List<PaymentResponse>> getMyReadyPayments(@RequestHeader(HEADER_USER_ID) String userPublicId) {
    return ok(getMyReadyPaymentsUseCase.getMyReadyPayments(userPublicId));
  }
}
