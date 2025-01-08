package com.ed.eventservice.coupon.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseJpaEntity {

  @ColumnDefault("false")
  private Boolean isDeleted;

  @CreatedDate
  @Column(updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime createdAt;

  @CreatedBy
  @Column(updatable = false, length = 36)
  private String createdBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedAt;

  @LastModifiedBy
  @Column(length = 36)
  private String updatedBy;

  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime deletedAt;

  @Column(length = 36)
  private String deletedBy;

  public void delete(UUID reqUserId) {
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = reqUserId.toString();
    this.isDeleted = true;
  }
}
