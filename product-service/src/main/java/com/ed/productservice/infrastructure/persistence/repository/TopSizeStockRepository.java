package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeStockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopSizeStockRepository extends JpaRepository<TopSizeStockEntity, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT t FROM TopSizeStockEntity t WHERE t.productId = :productId AND t.topSize = :size")
  TopSizeStockEntity findByProductIdAndTopSize(@Param("productId") Long productId,
      @Param("size") String size);
}
