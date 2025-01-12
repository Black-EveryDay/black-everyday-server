package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.size.BottomSizeStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BottomSizeStockRepository extends JpaRepository<BottomSizeStockEntity, Long> {

  @Query("SELECT b FROM BottomSizeStockEntity b WHERE b.productId = :productId AND b.bottomSize = :size")
  BottomSizeStockEntity findByProductIdAndBottomSize(@Param("productId") Long productId,
      @Param("size") String size);
}
