package com.ed.productservice.presentation.web;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import com.ed.productservice.presentation.port.in.ProductUseCase;
import com.ed.productservice.presentation.web.request.BottomProductCreateRequest;
import com.ed.productservice.presentation.web.request.ProductCommonInfo;
import com.ed.productservice.presentation.web.request.TopProductCreateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductCommandController.class)
@ActiveProfiles("test")
@Slf4j
class ProductCommandControllerWebMvcTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockitoBean
  private ProductUseCase productUseCase;

  @DisplayName("상의 상품 등록 성공하면 true, productPublicId, timestamp 반환")
  @Test
  void t1() throws Exception {
    String productPublicId = UUID.randomUUID().toString();
    LocalDateTime createdTime = LocalDateTime.now();

    TopProductCreateRequest topProductRequest = createTopProductRequest();

    Product mockProduct = new Product(
        1L,
        productPublicId,
        1L,
        "베이직 긴팔 티셔츠",
        29900,
        "편안한 착용감의 데일리 티셔츠",
        "BLACK",
        "top_image_url.jpg",
        ProductStatus.ACTIVE,
        ProductCategory.TOP,
        createdTime
    );

    when(productUseCase.createApparelTop(any(TopProduct.class))).thenReturn(mockProduct);

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

    verify(productUseCase).createApparelTop(any(TopProduct.class));
  }

  @DisplayName("하의 상품 등록 성공하면 true, productPublicId, timestamp 반환")
  @Test
  void t2() throws Exception {
    String productPublicId = UUID.randomUUID().toString();
    LocalDateTime createdTime = LocalDateTime.now();

    BottomProductCreateRequest bottomProductRequest = createBottomProductRequest();

    Product mockProduct = new Product(
        1L,
        productPublicId,
        1L,
        "스웻 팬츠",
        29900,
        "편안한 착용감의 데일리 팬츠",
        "BLACK",
        "bottom_image_url.jpg",
        ProductStatus.ACTIVE,
        ProductCategory.BOTTOM,
        createdTime
    );

    when(productUseCase.createApparelBottom(any(BottomProduct.class))).thenReturn(mockProduct);

    String requestBody = objectMapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(bottomProductRequest);

    mockMvc.perform(post("/api/v1/products/bottom")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.success").value(true))
        .andExpect(jsonPath("$.body.productPublicId").exists())
        .andExpect(jsonPath("$.body.createdAt").exists())
        .andExpect(jsonPath("$.timestamp").exists())
        .andDo(print());

    verify(productUseCase).createApparelBottom(any(BottomProduct.class));
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