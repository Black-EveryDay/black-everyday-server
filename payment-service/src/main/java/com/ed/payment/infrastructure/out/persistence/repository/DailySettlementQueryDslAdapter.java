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
      LocalDateTime startDateTime, LocalDateTime endDateTime, String currentId, int pageSize) {
    return queryFactory
        .select(Projections.constructor(AggregatedDailySettlement.class,
            dailySettlementJpaEntity.brandPublicId,
            dailySettlementJpaEntity.netRevenue.sum().as("totalNetRevenue"),
            dailySettlementJpaEntity.discountAmount.sum().as("totalDiscountAmount"),
            dailySettlementJpaEntity.commission.sum().as("totalCommission")))
        .from(dailySettlementJpaEntity)
        .where(dailySettlementJpaEntity.settledAt.between(startDateTime, endDateTime)
            .and(brandPublicIdGt(currentId)))
        .groupBy(dailySettlementJpaEntity.brandPublicId)
        .orderBy(dailySettlementJpaEntity.brandPublicId.asc())
        .limit(pageSize)
        .fetch();
  }

  private BooleanExpression brandPublicIdGt(String currentId) {
    return currentId != null ? dailySettlementJpaEntity.brandPublicId.gt(currentId) : null;
  }
}
