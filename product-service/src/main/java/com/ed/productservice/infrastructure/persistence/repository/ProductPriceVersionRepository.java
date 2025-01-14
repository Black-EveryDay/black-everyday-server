package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.ProductPriceVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPriceVersionRepository extends JpaRepository<ProductPriceVersionEntity, Long> {

}
