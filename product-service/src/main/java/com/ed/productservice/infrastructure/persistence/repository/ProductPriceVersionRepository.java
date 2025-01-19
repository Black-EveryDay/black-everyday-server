package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.ProductPriceVersionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductPriceVersionRepository extends JpaRepository<ProductPriceVersionEntity, Long> {

  @Query("select p from ProductPriceVersionEntity p where p.productId = :productId and p.isDeleted = false order by p.createdAt desc limit 1")
  Optional<ProductPriceVersionEntity> findByProductId(@Param("productId") Long productId);
}
