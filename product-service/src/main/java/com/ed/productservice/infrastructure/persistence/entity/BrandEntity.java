package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.vo.BrandType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_BRANDS")
@NoArgsConstructor
@Getter
public class BrandEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "BRAND_ID", nullable = false)
  private Long brandId;

  @Column(name = "BRAND_NAME", nullable = false)
  private String brandName;

  @Column(name = "BRAND_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private BrandType brandType;

  @Column(name = "BRAND_ADDRESS", nullable = false)
  private String brandAddress;

  public BrandEntity(String brandName, BrandType brandType, String brandAddress) {
    this.brandName = brandName;
    this.brandType = brandType;
    this.brandAddress = brandAddress;
  }
}