package com.ed.payment.libs.common.validator;

import com.ed.payment.libs.common.validator.dtos.PaymentValidatorRequest;

public interface PaymentValidator {
  void validate(PaymentValidatorRequest request);
}
