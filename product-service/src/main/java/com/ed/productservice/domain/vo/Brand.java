package com.ed.productservice.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Brand {
    private Long brandId;
    private String name;
    private BrandType type;
    private String address;
}
