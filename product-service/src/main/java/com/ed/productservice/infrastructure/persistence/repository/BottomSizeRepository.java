package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.BottomSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BottomSizeRepository extends JpaRepository<BottomSizeEntity, Long> {
}
