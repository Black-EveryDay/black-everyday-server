package com.ed.productservice.presentation.port.in;

import com.ed.productservice.domain.BottomProduct;
import com.ed.productservice.domain.TopProduct;
import com.ed.productservice.domain.vo.Product;

public interface ProductCreateUseCase {
    Product createApparelTop(TopProduct productForCreate);

    Product createApparelBottom(BottomProduct bottomProduct);
}
