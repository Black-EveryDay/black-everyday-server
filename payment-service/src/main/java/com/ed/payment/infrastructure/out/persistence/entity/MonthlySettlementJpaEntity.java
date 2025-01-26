package com.ed.payment.infrastructure.out.persistence.entity;

import static lombok.AccessLevel.PROTECTED;

import com.ed.payment.libs.common.entity.BaseTimeJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_MONTHLY_SETTLEMENT")
@NoArgsConstructor(access = PROTECTED)
public class MonthlySettlementJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MONTHLY_SETTLEMENT_ID")
    private Long id;

    @Column(name = "BRAND_PUBLIC_ID")
    private String brandPublicId;

    @Column(name = "TOTAL_NET_REVENUE")
    private BigDecimal totalNetRevenue;

    @Column(name = "TOTAL_DISCOUNT_AMOUNT")
    private BigDecimal totalDiscountAmount;

    @Column(name = "TOTAL_COMMISSION")
    private BigDecimal totalCommission;

    @Column(name = "SETTLED_AT")
    private LocalDateTime settledAt;
}