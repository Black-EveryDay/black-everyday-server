package com.ed.productservice.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
public class TopProduct {
    private ProductForCreate productForCreate;
    private List<TopSize> topSizeList;

    public TopProduct(ProductForCreate productForCreate, List<TopSize> topSizeList) {
        this.productForCreate = productForCreate;
        this.topSizeList = topSizeList;
    }

    @Getter
    public static class TopSize {
        private String topSize;
        private BigDecimal totalLength;
        private BigDecimal shoulderWidth;
        private BigDecimal chestWidth;
        private BigDecimal sleeveLength;

        public TopSize(String topSize, BigDecimal totalLength, BigDecimal shoulderWidth, BigDecimal chestWidth, BigDecimal sleeveLength) {
            this.topSize = topSize;
            this.totalLength = totalLength;
            this.shoulderWidth = shoulderWidth;
            this.chestWidth = chestWidth;
            this.sleeveLength = sleeveLength;
        }
    }
}
