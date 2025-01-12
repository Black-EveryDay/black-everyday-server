package com.ed.productservice.infrastructure.persistence.adapter.mapper;

import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.infrastructure.persistence.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {

  public Brand toDomain(BrandEntity entity) {
    return new Brand(
        entity.getBrandId(),
        entity.getBrandName(),
        entity.getBrandType(),
        entity.getBrandAddress());
  }
}
