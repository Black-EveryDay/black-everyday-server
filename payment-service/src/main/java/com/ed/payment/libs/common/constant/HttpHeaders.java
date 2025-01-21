package com.ed.payment.libs.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpHeaders {
  public static final String USER_ID = "X-User-Id";
  public static final String IDEMPOTENCY_KEY = "Idempotency-Key";
}
