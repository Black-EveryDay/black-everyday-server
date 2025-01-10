package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.BrandType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ed_brands")
@NoArgsConstructor
@Getter
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

  public BrandEntity(String brandName, BrandType brandType, String brandAddress) {
    this.brandName = brandName;
    this.brandType = brandType;
    this.brandAddress = brandAddress;
  }
}
