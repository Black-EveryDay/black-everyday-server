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
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_DAILY_SETTLEMENT")
@NoArgsConstructor(access = PROTECTED)
public class DailySettlementJpaEntity extends BaseTimeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DAILY_SETTLEMENT_ID")
    private Long id;

    @Column(name = "ORDER_PUBLIC_ID")
    private String orderPublicId;

    @Column(name = "BRAND_PUBLIC_ID")
    private String brandPublicId;

    @Column(name = "PRODUCT_PUBLIC_ID")
    private String productPublicId;

    @Column(name = "NET_REVENUE")
    private BigDecimal netRevenue;

    @Column(name = "DISCOUNT_AMOUNT")
    private BigDecimal discountAmount;

    @Column(name = "COMMISSION")
    private BigDecimal commission;

    @Column(name = "SETTLED_AT")
    private LocalDateTime settledAt;

    @Builder(builderMethodName = "createDailySettlement", builderClassName = "CreateDailySettlement")
    private DailySettlementJpaEntity(String brandPublicId, String orderPublicId,
        String productPublicId, BigDecimal netRevenue, BigDecimal discountAmount, BigDecimal commission) {
        this.orderPublicId = orderPublicId;
        this.brandPublicId = brandPublicId;
        this.productPublicId = productPublicId;
        this.netRevenue = netRevenue;
        this.discountAmount = discountAmount;
        this.commission = commission;
        this.settledAt = LocalDateTime.now();
    }
}