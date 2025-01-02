package com.ed.orderservice.libs.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponseUtils {

	public static <T> ResponseEntity<T> created(T response) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(response);
	}

	public static <T> ResponseEntity<T> noContent() {
		return ResponseEntity.noContent().build();
	}

}
