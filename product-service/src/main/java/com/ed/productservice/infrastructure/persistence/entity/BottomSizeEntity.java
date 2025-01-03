package com.ed.productservice.infrastructure.persistence.entity;

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

    @Column(name = "bottom_size", nullable = false)
    private String bottomSize;

    @Column(name = "total_length", nullable = false, precision = 5, scale = 1)
    private BigDecimal totalLength;

    @Column(name = "thigh_circumference", nullable = false, precision = 5, scale = 1)
    private BigDecimal thighCircumference;

    @Column(name = "hip_width", nullable = false, precision = 5, scale = 1)
    private BigDecimal hipWidth;

    public BottomSizeEntity(String bottomSize, BigDecimal totalLength, BigDecimal thighCircumference, BigDecimal hipWidth) {
        this.bottomSize = bottomSize;
        this.totalLength = totalLength;
        this.thighCircumference = thighCircumference;
        this.hipWidth = hipWidth;
    }
}