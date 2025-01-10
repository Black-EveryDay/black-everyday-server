package com.ed.payment.libs.common.entity;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseJpaEntity {

  @CreatedDate
  @Temporal(TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false, length = 36)
  private String createdBy;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  @Column
  private LocalDateTime updatedAt;

  @Column(length = 36)
  private String updatedBy;

  @Temporal(TIMESTAMP)
  @Column
  private LocalDateTime deletedAt;

  @Column(length = 36)
  private String deletedBy;

  @Column(nullable = false)
  private boolean isDeleted = false;

  public void createBy(String userUuid) {
    this.createdBy = userUuid;
  }

  public void updateBy(String userUuid) {
    this.updatedBy = userUuid;
  }

  public void softDeleteBy(String userUuid) {
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = userUuid;
    this.isDeleted = true;
  }
}
