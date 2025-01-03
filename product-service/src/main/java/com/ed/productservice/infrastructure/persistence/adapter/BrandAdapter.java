package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandAdapter implements BrandOutPort {
    private final BrandRepository brandRepository;

    @Override
    public Brand findOne(Long brandId) {
        return brandRepository.findById(brandId).orElseThrow(() -> new IllegalStateException("브랜드를 찾을 수 없습니다")).toDomain();
    }
}