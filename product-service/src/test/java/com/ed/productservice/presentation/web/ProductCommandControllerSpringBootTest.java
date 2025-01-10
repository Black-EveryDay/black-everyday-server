package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.vo.BrandType;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import com.ed.productservice.infrastructure.persistence.entity.BrandEntity;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import com.ed.productservice.presentation.web.request.BottomProductCreateRequest;
import com.ed.productservice.presentation.web.request.ProductCommonInfo;
import com.ed.productservice.presentation.web.request.TopProductCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Slf4j
class ProductCommandControllerSpringBootTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private BrandRepository brandRepository;
  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeEach
  void setUp() {
    jdbcTemplate.execute("TRUNCATE TABLE ed_brands");
  }

  @Transactional
  @DisplayName(value = "상의 상품 등록 성공하면 true, productPublicId, timestamp 반환 ")
  @Test
  void t1() throws Exception {
    brandRepository.save(new BrandEntity("테스트 브랜드", BrandType.CASUAL, "서울특별시"));

    TopProductCreateRequest topProductRequest = createTopProductRequest();

    String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(topProductRequest);

    mockMvc.perform(
            post("/api/v1/products/top").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.productPublicId").exists())
        .andExpect(jsonPath("$.body.createdAt").exists())
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());
  }

  @Transactional
  @DisplayName(value = "상의 상품 등록 시 브랜드 정보 존재하지 않으면 false, BrandNotFoundException 반환")
  @Test
  void t2() throws Exception {
    TopProductCreateRequest topProductRequest = createTopProductRequest();

    String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(topProductRequest);

    mockMvc.perform(
            post("/api/v1/products/top").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.body.status").value("NOT_FOUND"))
        .andExpect(jsonPath("$.body.message").value("해당 브랜드를 찾을 수 없습니다."))
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());
  }

  @Transactional
  @DisplayName(value = "하의 상품 등록 성공하면 true, productPublicId, timestamp 반환")
  @Test
  void t3() throws Exception {
    brandRepository.save(new BrandEntity("테스트 브랜드", BrandType.CASUAL, "서울특별시"));

    BottomProductCreateRequest bottomProductRequest = createBottomProductRequest();

    String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(bottomProductRequest);

    mockMvc.perform(post("/api/v1/products/bottom").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.productPublicId").exists())
        .andExpect(jsonPath("$.body.createdAt").exists())
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());
  }

  @Transactional
  @DisplayName(value = "하의 상품 등록 시 브랜드 정보 존재하지 않으면 false, BrandNotFoundException 반환")
  @Test
  void t4() throws Exception {
    BottomProductCreateRequest bottomProductRequest = createBottomProductRequest();

    String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(bottomProductRequest);

    mockMvc.perform(post("/api/v1/products/bottom").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.success").value(false))
        .andExpect(jsonPath("$.body.status").value("NOT_FOUND"))
        .andExpect(jsonPath("$.body.message").value("해당 브랜드를 찾을 수 없습니다."))
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());
  }

  private BottomProductCreateRequest createBottomProductRequest() {
    ProductCommonInfo productInfo = new ProductCommonInfo(
        1L,
        "스웻 팬츠",
        29900,
        "편안한 착용감의 데일리 팬츠",
        ProductStatus.ACTIVE,
        ProductCategory.BOTTOM,
        "BLACK",
        "bottom_image_url.jpg",
        100
    );

    List<BottomProductCreateRequest.BottomSizeRequest> sizeList = List.of(
        new BottomProductCreateRequest.BottomSizeRequest(
            "S",
            new BigDecimal("32.0"),
            new BigDecimal("44.0"),
            new BigDecimal("28.0"),
            new BigDecimal("98.0"),
            100
        ),
        new BottomProductCreateRequest.BottomSizeRequest(
            "M",
            new BigDecimal("34.0"),
            new BigDecimal("46.0"),
            new BigDecimal("30.0"),
            new BigDecimal("99.0"),
            100
        ),
        new BottomProductCreateRequest.BottomSizeRequest(
            "L",
            new BigDecimal("36.0"),
            new BigDecimal("48.0"),
            new BigDecimal("32.0"),
            new BigDecimal("100.0"),
            100
        )
    );

    return new BottomProductCreateRequest(productInfo, sizeList);
  }

  private static TopProductCreateRequest createTopProductRequest() {
    ProductCommonInfo productInfo = new ProductCommonInfo(
        1L,
        "베이직 긴팔 티셔츠",
        29900,
        "편안한 착용감의 데일리 티셔츠",
        ProductStatus.ACTIVE,
        ProductCategory.TOP,
        "BLACK",
        "top_image_url.jpg",
        100
    );

    List<TopProductCreateRequest.TopSizeRequest> sizeList = List.of(
        new TopProductCreateRequest.TopSizeRequest(
            "S",
            new BigDecimal("65.0"),
            new BigDecimal("42.0"),
            new BigDecimal("48.0"),
            new BigDecimal("61.0"),
            10
        ),
        new TopProductCreateRequest.TopSizeRequest(
            "M",
            new BigDecimal("67.0"),
            new BigDecimal("44.0"),
            new BigDecimal("50.0"),
            new BigDecimal("62.0"),
            10
        ),
        new TopProductCreateRequest.TopSizeRequest(
            "L",
            new BigDecimal("69.0"),
            new BigDecimal("46.0"),
            new BigDecimal("52.0"),
            new BigDecimal("63.0"),
            10
        )
    );

    return new TopProductCreateRequest(productInfo, sizeList);
  }
}