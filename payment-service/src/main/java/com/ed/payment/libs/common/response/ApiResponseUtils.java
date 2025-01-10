package com.ed.payment.libs.common.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseUtils {

  public static <T> ResponseEntity<T> ok(T response) {
    return ResponseEntity.ok(response);
  }
}