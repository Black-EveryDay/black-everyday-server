package com.ed.productservice.infrastructure.persistence.repository;

import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import java.util.Optional;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchProductRepository extends ElasticsearchRepository<SearchProduct, String> {

  Optional<SearchProduct> findByProductPublicId(String productPublicId);
}
