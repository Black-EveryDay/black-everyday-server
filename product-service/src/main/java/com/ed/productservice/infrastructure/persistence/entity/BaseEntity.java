package com.ed.productservice.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private Long createdBy;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    private Long updatedBy;

    private LocalDateTime deletedAt;
    private Long deletedBy;

    @Column(nullable = false)
    private boolean isDeleted = false;

    protected void createdFrom(Long createdBy) {
        this.createdBy = createdBy;
        this.updatedBy = createdBy;
    }

    protected void updatedFrom(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}