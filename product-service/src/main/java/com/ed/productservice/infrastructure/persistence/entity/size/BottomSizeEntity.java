package com.ed.productservice.infrastructure.persistence.entity.size;

import com.ed.productservice.infrastructure.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "ed_bottom_size")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BottomSizeEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bottom_size_id")
    private Long bottomSizeId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "bottom_size", nullable = false)
    private String bottomSize;

    @Column(name = "total_length", nullable = false, precision = 5, scale = 1)
    private BigDecimal totalLength;

    @Column(name = "thigh_circumference", nullable = false, precision = 5, scale = 1)
    private BigDecimal thighCircumference;

    @Column(name = "hip_width", nullable = false, precision = 5, scale = 1)
    private BigDecimal hipWidth;

    public BottomSizeEntity(Long productId, String bottomSize, BigDecimal totalLength, BigDecimal thighCircumference, BigDecimal hipWidth) {
        this.productId = productId;
        this.bottomSize = bottomSize;
        this.totalLength = totalLength;
        this.thighCircumference = thighCircumference;
        this.hipWidth = hipWidth;
    }
}