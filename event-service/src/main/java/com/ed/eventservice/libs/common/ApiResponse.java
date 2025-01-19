package com.ed.eventservice.libs.common;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

  private Boolean success;
  private T body;
  private LocalDateTime timestamp = LocalDateTime.now();

  public ApiResponse(Boolean success, T body) {
    this.success = success;
    this.body = body;
  }
}