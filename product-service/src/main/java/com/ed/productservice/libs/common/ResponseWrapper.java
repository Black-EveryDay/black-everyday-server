package com.ed.productservice.libs.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

public class ResponseWrapper implements ResponseBodyAdvice<Object> {

  private final AntPathMatcher pathMatcher = new AntPathMatcher();

  private boolean isExcludedPath(ServerHttpRequest request) {
    String path = request.getURI().getPath();
    return pathMatcher.match("/internal/metrics/prometheus/**", path);
  }

  @Override
  public boolean supports(
      MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return true;
  }

  @Override
  public Object beforeBodyWrite(Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response) {
    if (isExcludedPath(request)) {
      return body;
    }

    if (body instanceof ErrorResponse) {
      return new ApiResponse<>(false, body);
    }

    return new ApiResponse<>(true, body);
  }
}
