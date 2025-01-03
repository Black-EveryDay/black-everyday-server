package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.TopSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopSizeRepository extends JpaRepository<TopSizeEntity, Long> {
}
