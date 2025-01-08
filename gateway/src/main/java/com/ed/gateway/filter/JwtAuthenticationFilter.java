package com.ed.gateway.filter;

import com.ed.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

  private final JwtUtil jwtUtil;

  @Value("${service.gateway.secret-key}")
  private String gatewayKey;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String path = exchange.getRequest().getURI().getPath();

    if (path.matches("^/api/v\\d+/auth.*")) {
      return chain.filter(exchange);
    }

    String token = jwtUtil.extractToken(exchange);
    Optional<Claims> payload = jwtUtil.validateToken(token);

    if (token == null || payload.isEmpty()) {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    ServerHttpRequest build = exchange.getRequest().mutate()
        .header("X-User-Id", payload.get().get("userId").toString())
        .header("X-User-Role", payload.get().get("userRole").toString())
        .header("X-Gateway-Key", gatewayKey)
        .build();

    ServerWebExchange mutatedExchange = exchange.mutate().request(build).build();

    return chain.filter(mutatedExchange);
  }

}