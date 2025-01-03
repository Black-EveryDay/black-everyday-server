package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.BottomProduct;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "ed_bottom_size")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BottomSizeEntity extends BaseEntity{
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

    public static BottomSizeEntity from(Long productId, BottomProduct.BottomSize bottomSize) {
        return new BottomSizeEntity(
                productId,
                bottomSize.getBottomSize(),
                bottomSize.getBottomTotalLength(),
                bottomSize.getThighWidth(),
                bottomSize.getHipWidth()
        );
    }
}