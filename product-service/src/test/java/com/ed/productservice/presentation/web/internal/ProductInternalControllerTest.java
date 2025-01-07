package com.ed.productservice.presentation.web.internal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.productservice.application.service.internal.ProductInternalService;
import com.ed.productservice.domain.vo.BrandType;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import com.ed.productservice.infrastructure.persistence.entity.BrandEntity;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import com.ed.productservice.presentation.web.request.InventoryReservationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class ProductInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TopSizeStockRepository topSizeStockRepository;
    @Autowired
    private ProductInternalService productInternalService;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("TRUNCATE TABLE ed_brands");
        jdbcTemplate.execute("TRUNCATE TABLE ed_product");
        jdbcTemplate.execute("TRUNCATE TABLE ed_top_size_stock");

        BrandEntity brand = brandRepository.save(
            new BrandEntity("테스트 브랜드", BrandType.CASUAL, "서울특별시"));

        ProductEntity product = productRepository.save(
            createProduct(brand.getBrandId(), "맨투맨", "M"));
        ProductEntity product2 = productRepository.save(
            createProduct(brand.getBrandId(), "후드티", "L"));
        topSizeStockRepository.save(createTopSizeStock(product.getProductId(), "M"));
        topSizeStockRepository.save(createTopSizeStock(product2.getProductId(), "L"));
    }

    private TopSizeStockEntity createTopSizeStock(Long productId, String size) {
        return new TopSizeStockEntity(
            productId,
            size,
            new BigDecimal("65.0"),
            new BigDecimal("45.0"),
            new BigDecimal("50.0"),
            new BigDecimal("60.0"),
            100
        );
    }

    private ProductEntity createProduct(Long brandId, String name, String size) {
        return new ProductEntity(
            UUID.randomUUID().toString(),
            brandId,
            name,
            30000,
            "테스트 상품입니다",
            "BLACK",
            "test-image.jpg",
            ProductStatus.ACTIVE,
            ProductCategory.TOP
        );
    }

    @Test
    @DisplayName("재고 예약 요청시 reservationId를 반환한다")
    void t1() throws Exception {

        InventoryReservationRequest request = new InventoryReservationRequest(
            List.of(
                new InventoryReservationRequest.ProductReservationInfo(1L, 1, "M"),
                new InventoryReservationRequest.ProductReservationInfo(2L, 2, "L")
            )
        );

        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.body.reservationId").exists())
            .andExpect(jsonPath("$.timestamp").exists())
            .andDo(print());
    }

    @Test
    @DisplayName("재고보다 많은 수량을 예약 요청시 실패한다")
    void t2() throws Exception {
        InventoryReservationRequest request = new InventoryReservationRequest(
            List.of(
                new InventoryReservationRequest.ProductReservationInfo(1L, 1000, "M")
            )
        );

        String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.body.message").exists())
            .andExpect(jsonPath("$.body.status").value("BAD_REQUEST"))
            .andDo(print());
    }

    @Test
    @DisplayName("재고 차감 요청 시 reservationId 반환")
    void t3() throws Exception {
        InventoryReservationRequest request = new InventoryReservationRequest(
            List.of(
                new InventoryReservationRequest.ProductReservationInfo(1L, 5, "M"),
                new InventoryReservationRequest.ProductReservationInfo(2L, 5, "L")
            )
        );

        String reservationId = productInternalService.reservation(request);

        mockMvc.perform(
                post("/api/v1/products/internal/inventory/reservations/{reservationId}/decrease",
                    reservationId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.body.reservationId").value(reservationId))
            .andDo(print());
    }

    @Test
    @DisplayName("존재하지 않는 예약 ID로 재고 차감 요청시 실패한다")
    void t4() throws Exception {
        String reservationId = "testId";

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations/{reservationId}/decrease", reservationId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.body.message").exists())
            .andExpect(jsonPath("$.body.status").value("NOT_FOUND"))
            .andDo(print());
    }

    @Test
    @DisplayName("재고 차감 중복 요청 시 에러 반환")
    void t5() throws Exception {
        InventoryReservationRequest request = new InventoryReservationRequest(
            List.of(
                new InventoryReservationRequest.ProductReservationInfo(1L, 5, "M")
            )
        );

        String reservationId = productInternalService.reservation(request);

        mockMvc.perform(
                post("/api/v1/products/internal/inventory/reservations/{reservationId}/decrease",
                    reservationId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations/{reservationId}/decrease", reservationId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.body.message").exists())
            .andExpect(jsonPath("$.body.status").value("BAD_REQUEST"))
            .andDo(print());
    }

    @Test
    @DisplayName("재고 롤백 요청 후 reservationId 반환")
    void t6() throws Exception {
        InventoryReservationRequest request = new InventoryReservationRequest(
            List.of(
                new InventoryReservationRequest.ProductReservationInfo(1L, 5, "M")
            )
        );

        String reservationId = productInternalService.reservation(request);

        mockMvc.perform(
                post("/api/v1/products/internal/inventory/reservations/{reservationId}/decrease",
                    reservationId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations/{reservationId}/increase", reservationId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.body.reservationId").value(reservationId))
            .andDo(print());
    }

    @Test
    @DisplayName("재고 차감 롤백 요청 시 history 테이블에 존재하지 않는 예약정보면 404 반환")
    void t7() throws Exception {
        String reservationId = "testId";

        mockMvc.perform(post("/api/v1/products/internal/inventory/reservations/{reservationId}/increase", reservationId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.success").value(false))
            .andExpect(jsonPath("$.body.status").value("NOT_FOUND"))
            .andDo(print());
    }

}