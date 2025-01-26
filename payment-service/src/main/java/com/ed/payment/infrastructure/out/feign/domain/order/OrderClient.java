package com.ed.payment.infrastructure.out.feign.domain.order;

import com.ed.payment.application.port.out.feign.dtos.OrderSettlementResponse;
import com.ed.payment.infrastructure.out.feign.config.FeignClientConfig;
import com.ed.payment.libs.common.response.ApiResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", configuration = FeignClientConfig.class)
interface OrderClient {

  @PostMapping("/api/v1/internal/orders/settlements")
  ApiResponse<List<OrderSettlementResponse>> getOrderSettlement(@RequestBody List<String> orderPublicIds);
}
