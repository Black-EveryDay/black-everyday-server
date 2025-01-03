package com.ed.productservice.libs.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
