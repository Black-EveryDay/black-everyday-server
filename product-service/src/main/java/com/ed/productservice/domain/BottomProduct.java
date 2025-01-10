package com.ed.productservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class BottomProduct {

  private ProductForCreate productForCreate;
  private List<BottomSize> bottomSizeList;

  public BottomProduct(ProductForCreate productForCreate, List<BottomSize> bottomSizeList) {
    this.productForCreate = productForCreate;
    this.bottomSizeList = bottomSizeList;
  }

  @Getter
  @NoArgsConstructor
  public static class BottomSize {

    private String bottomSize;
    private BigDecimal waistWidth;
    private BigDecimal hipWidth;
    private BigDecimal thighWidth;
    private BigDecimal bottomTotalLength;
    private Integer quantity;

    public BottomSize(String bottomSize, BigDecimal waistWidth, BigDecimal hipWidth,
        BigDecimal thighWidth, BigDecimal bottomTotalLength, Integer quantity) {
      this.bottomSize = bottomSize;
      this.waistWidth = waistWidth;
      this.hipWidth = hipWidth;
      this.thighWidth = thighWidth;
      this.bottomTotalLength = bottomTotalLength;
      this.quantity = quantity;
    }
  }
}
