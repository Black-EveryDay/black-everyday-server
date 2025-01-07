package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockDecreaseHistoryRepository extends
    JpaRepository<StockDecreaseHistoryEntity, Long> {

    @Query("SELECT s FROM StockDecreaseHistoryEntity s WHERE s.reservationId = :reservationId AND s.isDeleted = false")
    List<StockDecreaseHistoryEntity> findAllByReservationId(@Param("reservationId") String reservationId);

    @Query("select s from StockDecreaseHistoryEntity s where s.reservationId = :reservationId and s.productId = :productId and s.size =:size")
    List<StockDecreaseHistoryEntity> findByProductIdAndSizeAndReservationId(@Param("productId") Long productId, @Param("size") String size,
        @Param("reservationId") String reservationId);

}
