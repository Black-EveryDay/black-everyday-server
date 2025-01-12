package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.domain.vo.StockDecreaseHistoryStatus;
import com.ed.productservice.infrastructure.persistence.entity.StockDecreaseHistoryEntity;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StockDecreaseHistoryRepository extends
    JpaRepository<StockDecreaseHistoryEntity, Long> {

  @Query("SELECT s FROM StockDecreaseHistoryEntity s WHERE s.transactionId = :transactionId AND s.isDeleted = false")
  List<StockDecreaseHistoryEntity> findAllByTransactionId(
      @Param("transactionId") String transactionId);

  @Query("select s from StockDecreaseHistoryEntity s where s.transactionId = :transactionId and s.productPublicId = :productPublicId and s.size =:size")
  List<StockDecreaseHistoryEntity> findByProductIdAndSizeAndTransactionId(
      @Param("productPublicId") String productPublicId, @Param("size") String size,
      @Param("transactionId") String transactionId);

  @Query("SELECT s FROM StockDecreaseHistoryEntity s " +
      "WHERE s.status = :status " +
      "AND s.isDeleted = false " +
      "AND s.createdAt < :timeLimit")
  List<StockDecreaseHistoryEntity> findUncommittedStocks(
      @Param("timeLimit") LocalDateTime timeLimit,
      @Param("status") StockDecreaseHistoryStatus status);
}
