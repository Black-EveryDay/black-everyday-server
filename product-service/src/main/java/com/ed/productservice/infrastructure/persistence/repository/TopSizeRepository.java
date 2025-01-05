package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.entity.size.TopSizeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopSizeRepository extends JpaRepository<TopSizeEntity, Long> {
}
