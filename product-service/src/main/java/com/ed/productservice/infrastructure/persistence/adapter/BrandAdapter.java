package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import com.ed.productservice.libs.common.BrandNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.ed.productservice.libs.common.ErrorCode.BRAND_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BrandAdapter implements BrandOutPort {
    private final BrandRepository brandRepository;

    @Override
    public Brand findOne(Long brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> new BrandNotFoundException(BRAND_NOT_FOUND)).toDomain();
    }
}