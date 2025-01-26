package com.ed.eventservice.libs.common;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (requestAttributes != null) {
      HttpServletRequest request = requestAttributes.getRequest();

      // 회원가입, 로그인인 경우 넘어감
      String requestURI = request.getRequestURI();
      if (requestURI.matches("^/api/v\\d+/auth.*")) {
        return Optional.empty();
      }

      if (Objects.isNull(request.getHeader("X-User-Id"))) {
        return Optional.empty();
      }

      return Optional.of(UUID.fromString(request.getHeader("X-User-Id")).toString());
    }
    return Optional.empty();
  }

}
