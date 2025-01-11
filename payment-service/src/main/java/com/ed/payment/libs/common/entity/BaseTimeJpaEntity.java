package com.ed.payment.libs.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeJpaEntity {

  @CreatedDate
  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedAt;

  @Column
  private LocalDateTime deletedAt;

  @Column(nullable = false)
  private boolean isDeleted = false;

  public void softDelete() {
    deletedAt = LocalDateTime.now();
    isDeleted = true;
  }
}
