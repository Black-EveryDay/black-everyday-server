package com.ed.productservice.presentation.web.internal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ed.productservice.domain.vo.BrandType;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import com.ed.productservice.infrastructure.persistence.entity.BrandEntity;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import com.ed.productservice.infrastructure.persistence.repository.ProductRepository;
import com.ed.productservice.infrastructure.persistence.repository.TopSizeStockRepository;
import com.ed.productservice.libs.common.ApiResponse;
import com.ed.productservice.presentation.web.request.StockPrepareRequest;
import com.ed.productservice.presentation.web.request.StockPrepareRequest.ProductReservationInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
@TestPropertySource(locations = "classpath:application-test.yml") // application-test.yml만 로드
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

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("TRUNCATE TABLE ed_brands");
    jdbcTemplate.execute("TRUNCATE TABLE ed_product");
    jdbcTemplate.execute("TRUNCATE TABLE ed_top_size_stock");
    jdbcTemplate.execute("ALTER TABLE ed_brands ALTER COLUMN brand_id RESTART WITH 1");

    createTestData();
  }


  @Test
  @DisplayName("재고 차감 요청 시 transactionId, brandId, productId 반환")
  void t1() throws Exception {
    List<ProductEntity> products = createProducts();

    StockPrepareRequest request = new StockPrepareRequest(
        requestForCreateProduct(products.get(0), products.get(1))
    );

    String content = objectMapper.writeValueAsString(request);

    mockMvc.perform(
            post("/api/v1/products/internal/prepare")
                .contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.transactionId").exists())
        .andExpect(jsonPath("$.body.productBrandInfoList[1].brandId").exists())
        .andExpect(jsonPath("$.body.productBrandInfoList[1].productId").exists())
        .andDo(print());
  }

  @Test
  @DisplayName("재고보다 많은 수량을 예약 요청시 실패한다")
  void t2() throws Exception {
    List<ProductEntity> products = createProducts();

    StockPrepareRequest request = new StockPrepareRequest(
        requestForCreateProductV2(products.get(0), products.get(1))
    );

    String content = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

    mockMvc.perform(post("/api/v1/products/internal/prepare")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.body.message").exists())
        .andExpect(jsonPath("$.body.status").value("CONFLICT"))
        .andDo(print());
  }

  @Test
  @DisplayName("재고 롤백 요청 후 transactionId 반환")
  void t3() throws Exception {
    List<ProductEntity> products = createProducts();

    StockPrepareRequest request = new StockPrepareRequest(
        requestForCreateProduct(products.get(0), products.get(1))
    );

    String content = objectMapper.writeValueAsString(request);

    MvcResult result = mockMvc.perform(
            post("/api/v1/products/internal/prepare")
                .contentType(MediaType.APPLICATION_JSON).content(content))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.transactionId").exists())
        .andDo(print()).andReturn();

    String responseBody = result.getResponse().getContentAsString();

    ApiResponse response = objectMapper.readValue(responseBody, ApiResponse.class);
    String transactionId = ((Map) response.getBody()).get("transactionId").toString();

    mockMvc.perform(
            post("/api/v1/products/internal/rollback/{transactionId}", transactionId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.transactionId").value(transactionId))
        .andDo(print());
  }

  @Test
  @DisplayName("재고 차감 롤백 요청 시 history 테이블에 이력 존재하지 않으면 404 반환")
  void t4() throws Exception {
    String transactionId = "testId";

    mockMvc.perform(
            post("/api/v1/products/internal/rollback/{transactionId}", transactionId)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.body.status").value("NOT_FOUND"))
        .andDo(print());
  }

  private List<ProductEntity> createProducts() {
    ProductEntity productEntity1 = createProduct(1L, "맨투맨");
    ProductEntity productEntity2 = createProduct(1L, "후드티");

    createTopSizeStock(productEntity1.getProductId(), "M");
    createTopSizeStock(productEntity2.getProductId(), "L");

    return List.of(productEntity1, productEntity2);
  }

  private static List<ProductReservationInfo> requestForCreateProduct(ProductEntity productEntity1,
      ProductEntity productEntity2) {
    return List.of(
        new ProductReservationInfo(productEntity1.getProductPublicId(), 2, "M",
            ProductCategory.TOP),
        new ProductReservationInfo(productEntity2.getProductPublicId(), 3, "L", ProductCategory.TOP)
    );
  }

  private static List<ProductReservationInfo> requestForCreateProductV2(
      ProductEntity productEntity1,
      ProductEntity productEntity2) {
    return List.of(
        new ProductReservationInfo(productEntity1.getProductPublicId(), 1000000, "M",
            ProductCategory.TOP),
        new ProductReservationInfo(productEntity2.getProductPublicId(), 2000000, "L",
            ProductCategory.TOP)
    );
  }

  private void createTestData() {
    BrandEntity save = brandRepository.save(
        new BrandEntity("테스트 브랜드", BrandType.CASUAL, "서울특별시"));
    log.info("save brand {}" ,save.getBrandId());
  }

  private TopSizeStockEntity createTopSizeStock(Long productId, String size) {
    return topSizeStockRepository.save(new TopSizeStockEntity(
        productId,
        size,
        new BigDecimal("65.0"),
        new BigDecimal("45.0"),
        new BigDecimal("50.0"),
        new BigDecimal("60.0"),
        100
    ));
  }

  private ProductEntity createProduct(Long brandId, String name) {
    return productRepository.save(new ProductEntity(
        UUID.randomUUID().toString(),
        brandId,
        name,
        30000,
        "테스트 상품입니다",
        "BLACK",
        "test-image.jpg",
        ProductStatus.ACTIVE,
        ProductCategory.TOP
    ));
  }
}