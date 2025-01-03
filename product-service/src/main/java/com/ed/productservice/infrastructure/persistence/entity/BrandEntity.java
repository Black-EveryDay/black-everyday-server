package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.Brand;
import com.ed.productservice.domain.vo.BrandType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ed_brands")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)

public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "brand_name", nullable = false)
    private String brandName;

    @Enumerated(EnumType.STRING)
    @Column(name = "brand_type", nullable = false)
    private BrandType brandType;

    @Column(name = "brand_address", nullable = false)
    private String brandAddress;

    public Brand toDomain() {
        return new Brand(brandId, brandName, brandType, brandAddress);
    }
}
