package com.ed.productservice.application.port.out;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;

public interface ProductDetailPort {

    void saveTopSize(Long product, TopProduct domain);
    void saveBottomSize(Long productId, BottomProduct bottomProduct);
}
