package com.ed.productservice.application.port.out;

import com.ed.productservice.domain.vo.Brand;

public interface BrandOutPort {
    Brand findOne(Long brandId);
}
