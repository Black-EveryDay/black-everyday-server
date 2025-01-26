package com.ed.payment.infrastructure.out.persistence.repository;

import static com.ed.payment.infrastructure.out.persistence.entity.QDailySettlementJpaEntity.dailySettlementJpaEntity;

import com.ed.payment.application.port.out.persistence.GetDailySettlementPort;
import com.ed.payment.application.port.out.persistence.dtos.AggregatedDailySettlement;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class DailySettlementQueryDslAdapter implements GetDailySettlementPort {

  private final JPAQueryFactory queryFactory;

  @Override
  public List<AggregatedDailySettlement> aggregateLastMonthDailySettlements(
      LocalDateTime startDateTime, LocalDateTime endDateTime, Long currentId, int pageSize) {
    return queryFactory
        .select(Projections.constructor(AggregatedDailySettlement.class,
            dailySettlementJpaEntity.id,
            dailySettlementJpaEntity.brandPublicId,
            dailySettlementJpaEntity.netRevenue.sum().as("totalNetRevenue"),
            dailySettlementJpaEntity.discountAmount.sum().as("totalDiscountAmount"),
            dailySettlementJpaEntity.commission.sum().as("totalCommission")))
        .from(dailySettlementJpaEntity)
        .where(dailySettlementJpaEntity.settledAt.between(startDateTime, endDateTime)
            .and(dailySettlementIdGt(currentId)))
        .groupBy(dailySettlementJpaEntity.brandPublicId, dailySettlementJpaEntity.id)
        .orderBy(dailySettlementJpaEntity.id.asc())
        .limit(pageSize)
        .fetch();
  }


  private BooleanExpression dailySettlementIdGt(Long currentId) {
    return currentId != null ? dailySettlementJpaEntity.id.gt(currentId) : null;
  }
}
