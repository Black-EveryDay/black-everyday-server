package com.ed.payment.infrastructure.out.persistence.repository;

import static com.ed.payment.domain.PaymentStatus.DONE;
import static com.ed.payment.domain.PaymentStatus.PARTIAL_CANCELED;
import static com.ed.payment.infrastructure.out.persistence.entity.QPaymentJpaEntity.paymentJpaEntity;

import com.ed.payment.application.port.out.persistence.dtos.SettleablePaymentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class PaymentQueryDslAdapter {

  private final JPAQueryFactory queryFactory;

  public List<SettleablePaymentResponse> findSettleablePayments(LocalDateTime requestDateTime, Long currentId, int pageSize) {
    return queryFactory
        .select(Projections.constructor(SettleablePaymentResponse.class,
            paymentJpaEntity.id, paymentJpaEntity.orderPublicId))
        .from(paymentJpaEntity)
        .where(paymentJpaEntity.paymentStatus.in(DONE, PARTIAL_CANCELED)
            .and(paymentJpaEntity.cancelDeadLine.before(requestDateTime))
            .and(paymentIdGt(currentId)))
        .orderBy(paymentJpaEntity.id.asc())
        .limit(pageSize)
        .fetch();
  }

  private BooleanExpression paymentIdGt(Long currentId) {
    return currentId != null ? paymentJpaEntity.id.gt(currentId) : null;
  }
}
