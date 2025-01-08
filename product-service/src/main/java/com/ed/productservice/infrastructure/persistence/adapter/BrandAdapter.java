package com.ed.productservice.infrastructure.persistence.adapter;

import com.ed.productservice.application.port.out.BrandOutPort;
import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.infrastructure.persistence.adapter.mapper.BrandMapper;
import com.ed.productservice.infrastructure.persistence.entity.BrandEntity;
import com.ed.productservice.infrastructure.persistence.repository.BrandRepository;
import com.ed.productservice.libs.common.BrandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.ed.productservice.libs.common.ErrorCode.BRAND_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BrandAdapter implements BrandOutPort {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public Brand findOne(Long brandId) {
        BrandEntity entity = brandRepository.findById(brandId).orElseThrow(() -> new BrandException(BRAND_NOT_FOUND));

        return brandMapper.toDomain(entity);
    }
}