package com.ed.productservice.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

  @CreatedDate
  @Column(name = "CREATED_AT", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "CREATED_BY")
  private Long createdBy;

  @LastModifiedDate
  @Column(name = "UPDATED_AT", nullable = false)
  private LocalDateTime updatedAt;

  @Column(name = "UPDATED_BY")
  private Long updatedBy;

  @Column(name = "DELETED_AT")
  private LocalDateTime deletedAt;

  @Column(name = "DELETED_BY")
  private Long deletedBy;

  @Column(name = "IS_DELETED", nullable = false)
  private boolean isDeleted = false;

  protected void createdFrom(Long createdBy) {
    this.createdBy = createdBy;
    this.updatedBy = createdBy;
  }

  protected void updatedFrom(Long updatedBy) {
    this.updatedBy = updatedBy;
  }

  public void deletedFrom() {
    this.deletedAt = LocalDateTime.now();
    this.isDeleted = true;
  }
}