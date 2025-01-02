package com.ed.orderservice.libs.common;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
	private Boolean success;
	private T body;
	private LocalDateTime timestamp = LocalDateTime.now();

	public ApiResponse(Boolean success, T body) {
		this.success = success;
		this.body = body;
	}
}
