package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.domain.vo.ProductDetails;
import com.ed.productservice.infrastructure.persistence.entity.ProductEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  @Query("SELECT new com.ed.productservice.domain.vo.ProductDetails(" +
      "p.productPublicId, p.color, p.image, p.category, p.description, " +
      "p.name, pv.price, b.brandName) " +
      "FROM ProductEntity p " +
      "JOIN BrandEntity b ON p.brandId = b.brandId " +
      "JOIN ProductPriceVersionEntity pv ON p.productId = pv.productId "+
      "WHERE p.productPublicId = :productPublicId " +
      "AND p.isDeleted is false " +
      "AND pv.isCurrentVersion is true ")
  Optional<ProductDetails> findByProductAndCurrentPrice(@Param("productPublicId") String productPublicId);

  Optional<ProductEntity> findByProductPublicId(@Param("productPublicId") String productPublicId);

  @Query("SELECT new com.ed.productservice.domain.vo.ProductDetails(" +
      "p.productPublicId, p.color, p.image, p.category, p.description, " +
      "p.name, pv.price, b.brandName) " +
      "FROM ProductEntity p " +
      "JOIN BrandEntity b ON p.brandId = b.brandId " +
      "JOIN ProductPriceVersionEntity pv ON p.productId = pv.productId "+
      "WHERE p.productPublicId = :productPublicId " +
      "AND p.isDeleted is false " +
      "AND pv.version = :version ")
  Optional<ProductDetails> findByProductAndPriceVersion(@Param("productPublicId") String productPublicId,@Param("version") int version);

}
