package com.ed.payment.libs.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseTimeByJpaEntity extends BaseTimeJpaEntity {

  @Column(name = "CREATED_BY", length = 36)
  private String createdBy;

  @Column(name = "UPDATED_BY", length = 36)
  private String updatedBy;

  @Column(name = "DELETED_BY", length = 36)
  private String deletedBy;

  public void createBy(String userUuid) {
    this.createdBy = userUuid;
  }

  public void updateBy(String userUuid) {
    this.updatedBy = userUuid;
  }

  public void softDeleteBy(String userUuid) {
    super.softDelete();
    this.deletedBy = userUuid;
  }
}
