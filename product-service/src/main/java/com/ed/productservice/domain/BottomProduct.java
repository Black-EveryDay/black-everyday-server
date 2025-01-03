package com.ed.productservice.domain;

import lombok.Getter;

@Getter
public class BottomProduct {
    private ProductForCreate productForCreate;
    private String bottomSize;
    private int waistWidth;
    private int hipWidth;
    private int thighWidth;
    private int bottomTotalLength;

    public BottomProduct(ProductForCreate productForCreate, String bottomSize, int waistWidth, int hipWidth, int thighWidth, int bottomTotalLength) {
        this.productForCreate = productForCreate;
        this.bottomSize = bottomSize;
        this.waistWidth = waistWidth;
        this.hipWidth = hipWidth;
        this.thighWidth = thighWidth;
        this.bottomTotalLength = bottomTotalLength;
    }
}
