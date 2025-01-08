package com.ed.productservice.application.service.internal;

import static com.ed.productservice.libs.common.ErrorCode.INVENTORY_RESERVATION_FAILED;
import static com.ed.productservice.libs.common.ErrorCode.STOCK_RESERVATION_NOT_FOUND;

import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductReservationInfoDomain;
import com.ed.productservice.libs.common.ProductException;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest.ProductReservationInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProductInternalService {

    private final ProductOutPort productOutPort;
    private final RedisTemplate<String, String> redisTemplate;
    private final StockService stockService;
    private final ObjectMapper objectMapper;
    private final String STOCK_RESERVATION = "product-stock:reservation:";

    @Transactional
    public String reservation(InventoryReservationRequest request) {
        String reservationId = UUID.randomUUID().toString();

        try {
            List<ProductReservationInfo> items = request.items();
            for (ProductReservationInfo item : items) {

                Product product = productOutPort.findOne(item.productId());
                ProductCategory category = product.getCategory();

                stockService.stockReservation(item, category);

                saveStockReservation(item, reservationId, category);
            }
        } catch (Exception e) {
            redisTemplate.delete(STOCK_RESERVATION + reservationId);
            log.error("reservationId = {}, error message = {}", reservationId, e.getMessage());
            throw new ProductException(INVENTORY_RESERVATION_FAILED);
        }

        return reservationId;
    }

    @Transactional
    public String decreaseStock(String reservationId) throws JsonProcessingException {
        String key = STOCK_RESERVATION + reservationId;

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
        if (entries.isEmpty()) {
            throw new ProductException(STOCK_RESERVATION_NOT_FOUND);
        }

        for (Entry<Object, Object> entry : entries.entrySet()) {
            ProductReservationInfoDomain productReservationInfo = objectMapper.readValue(
                entry.getValue().toString(), ProductReservationInfoDomain.class);

            stockService.decreaseStockReservation(productReservationInfo, reservationId);
        }

        return reservationId;
    }

    @Transactional
    public String increaseStock(String reservationId) {
        stockService.increaseStock(reservationId);

        return reservationId;
    }


    private void saveStockReservation(ProductReservationInfo item, String reservationId,
        ProductCategory category)
        throws JsonProcessingException {
        String key = STOCK_RESERVATION + reservationId;
        String hashKey = item.productId() + ":" + item.size();

        redisTemplate.opsForHash().put(key, hashKey, objectMapper.writeValueAsString(ProductReservationInfoDomain.of(item, category)));

        redisTemplate.expire(key, Duration.ofHours(1));
    }
}