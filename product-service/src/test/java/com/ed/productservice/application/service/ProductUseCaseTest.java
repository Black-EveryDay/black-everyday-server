package com.ed.productservice.application.service;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.application.port.out.ProductDetailPort;
import com.ed.productservice.application.port.out.ProductOutPort;
import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Slf4j
class ProductUseCaseTest {

  @Mock
  private ProductOutPort productOutPort;
  @Mock
  private BrandOutPort brandOutPort;
  @Mock
  private ProductDetailPort productDetailPort;
  @InjectMocks
  private ProductCommandService productCommandService;

  @Test
  @DisplayName("상품 생성 성공 후 Product 반환")
  void t1() {
    ProductForCreate productForCreate = new ProductForCreate(
        1L,
        "베이직 긴팔 티셔츠",
        29900,
        "편안한 착용감의 데일리 티셔츠",
        ProductStatus.ACTIVE,
        ProductCategory.TOP,
        "BLACK",
        100,
        "top_image_url.jpg"
    );

    List<TopProduct.TopSize> topSizeList = List.of(
        new TopProduct.TopSize(
            "S",
            new BigDecimal("65.0"),
            new BigDecimal("42.0"),
            new BigDecimal("48.0"),
            new BigDecimal("61.0"),
            100
        )
    );

    TopProduct topProduct = new TopProduct(productForCreate, topSizeList);

    Brand mockBrand = new Brand(1L, "테스트 브랜드", BrandType.CASUAL, "서울특별시");

    Product mockProduct = new Product(
        1L,
        UUID.randomUUID().toString(),
        1L,
        "베이직 긴팔 티셔츠",
        29900,
        "편안한 착용감의 데일리 티셔츠",
        "BLACK",
        "top_image_url.jpg",
        ProductStatus.ACTIVE,
        ProductCategory.TOP,
        LocalDateTime.now()
    );

    when(brandOutPort.findOne(1L)).thenReturn(mockBrand);
    when(productOutPort.createProduct(any(ProductForCreate.class), eq(1L))).thenReturn(mockProduct);
    doNothing().when(productDetailPort).saveTopSize(eq(1L), any(TopProduct.class));

    Product result = productCommandService.createApparelTop(topProduct);

    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.getProductId()).isEqualTo(1L),
        () -> assertThat(result.getBrandId()).isEqualTo(1L),
        () -> assertThat(result.getName()).isEqualTo("베이직 긴팔 티셔츠")
    );

    verify(brandOutPort).findOne(1L);
    verify(productOutPort).createProduct(any(ProductForCreate.class), eq(1L));
    verify(productDetailPort).saveTopSize(eq(1L), any(TopProduct.class));
  }

  @Test
  @DisplayName("바지 상품 생성 성공 후 Product 반환")
  void createApparelBottom_Success() {
    // Given
    ProductForCreate productForCreate = new ProductForCreate(
        1L,
        "스웻 팬츠",
        29900,
        "편안한 착용감의 데일리 팬츠",
        ProductStatus.ACTIVE,
        ProductCategory.BOTTOM,
        "BLACK",
        100,
        "bottom_image_url.jpg"
    );

    List<BottomProduct.BottomSize> bottomSizeList = List.of(
        new BottomProduct.BottomSize(
            "S",
            new BigDecimal("32.0"),
            new BigDecimal("44.0"),
            new BigDecimal("28.0"),
            new BigDecimal("98.0"),
            10
        )
    );

    BottomProduct bottomProduct = new BottomProduct(productForCreate, bottomSizeList);

    Brand mockBrand = new Brand(1L, "테스트 브랜드", BrandType.CASUAL, "서울특별시");

    Product mockProduct = new Product(
        1L,
        UUID.randomUUID().toString(),
        1L,
        "스웻 팬츠",
        29900,
        "편안한 착용감의 데일리 팬츠",
        "BLACK",
        "bottom_image_url.jpg",
        ProductStatus.ACTIVE,
        ProductCategory.BOTTOM,
        LocalDateTime.now()
    );

    when(brandOutPort.findOne(1L)).thenReturn(mockBrand);
    when(productOutPort.createProduct(any(ProductForCreate.class), eq(1L))).thenReturn(mockProduct);
    doNothing().when(productDetailPort).saveBottomSize(eq(1L), any(BottomProduct.class));

    Product result = productCommandService.createApparelBottom(bottomProduct);

    assertAll(
        () -> assertThat(result).isNotNull(),
        () -> assertThat(result.getProductId()).isEqualTo(1L),
        () -> assertThat(result.getBrandId()).isEqualTo(1L),
        () -> assertThat(result.getName()).isEqualTo("스웻 팬츠"),
        () -> assertThat(result.getCategory()).isEqualTo(ProductCategory.BOTTOM)
    );

    verify(brandOutPort).findOne(1L);
    verify(productOutPort).createProduct(any(ProductForCreate.class), eq(1L));
    verify(productDetailPort).saveBottomSize(eq(1L), any(BottomProduct.class));
  }
}