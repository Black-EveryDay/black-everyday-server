package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.TopProduct;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "ed_top_size")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TopSizeEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "top_size_id")
    private Long topSizeId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "top_size", nullable = false)
    private String topSize;

    @Column(name = "total_length", nullable = false)
    private BigDecimal totalLength;

    @Column(name = "shoulder_width", nullable = false)
    private BigDecimal shoulderWidth;

    @Column(name = "chest_width", nullable = false)
    private BigDecimal chestWidth;

    @Column(name = "sleeve_length", nullable = false)
    private BigDecimal sleeveLength;

    public TopSizeEntity(Long productId, String topSize, BigDecimal totalLength, BigDecimal shoulderWidth, BigDecimal chestWidth, BigDecimal sleeveLength) {
        this.productId = productId;
        this.topSize = topSize;
        this.totalLength = totalLength;
        this.shoulderWidth = shoulderWidth;
        this.chestWidth = chestWidth;
        this.sleeveLength = sleeveLength;
    }

    public static TopSizeEntity from(Long productId, TopProduct.TopSize topSize) {
        return new TopSizeEntity(
                productId,
                topSize.getTopSize(),
                topSize.getTotalLength(),
                topSize.getShoulderWidth(),
                topSize.getChestWidth(),
                topSize.getSleeveLength()
        );
    }
}