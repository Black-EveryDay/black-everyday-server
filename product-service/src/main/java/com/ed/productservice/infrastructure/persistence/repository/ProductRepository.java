package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.domain.vo.ProductInfoDto;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

    @Query("SELECT new com.ed.productservice.domain.vo.ProductInfoDto(" +
        "p.productId, p.color, p.image, p.category, p.description, " +
        "p.name, p.price, b.brandName) " +
        "FROM ProductEntity p " +
        "JOIN BrandEntity b ON p.brandId = b.brandId " +
        "WHERE p.productId = :productId " +
        "AND p.isDeleted is false")
    Optional<ProductInfoDto> findByProductInfo(@Param("productId") Long productId);
}
