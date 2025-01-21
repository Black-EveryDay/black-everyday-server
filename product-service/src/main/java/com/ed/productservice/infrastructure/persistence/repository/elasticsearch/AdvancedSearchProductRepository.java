package com.ed.productservice.infrastructure.persistence.repository.elasticsearch;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.ed.productservice.infrastructure.persistence.elasticsearch.SearchProduct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
@RequiredArgsConstructor
public class AdvancedSearchProductRepository {

  private final ElasticsearchOperations elasticsearchOperations;

  public Page<SearchProduct> search(
      String productName,
      String color,
      String category,
      Integer minPrice,
      Integer maxPrice,
      String brandName,
      Pageable pageable) {

    BoolQuery.Builder boolQueryBuilder = QueryBuilders.bool();

    boolQueryBuilder.filter(q -> q.term(t -> t
        .field("is_deleted")
        .value(false)
    ));

    if (StringUtils.hasText(productName)) {
      boolQueryBuilder.must(q -> q.wildcard(w -> w
          .field("name")
          .value("*" + productName.toLowerCase() + "*")
      ));
    }

    if (StringUtils.hasText(color)) {
      boolQueryBuilder.filter(q -> q.term(t -> t
          .field("color")
          .value(color)
      ));
    }

    if (category != null) {
      boolQueryBuilder.filter(q -> q.term(t -> t
          .field("category")
          .value(category)
      ));
    }

    if (minPrice != null || maxPrice != null) {
      boolQueryBuilder.filter(q -> q.range(r -> {
        var rangeQuery = r.field("price");
        if (minPrice != null) {
          rangeQuery.gte(JsonData.of(minPrice));
        }
        if (maxPrice != null) {
          rangeQuery.lte(JsonData.of(maxPrice));
        }
        return rangeQuery;
      }));
    }

    if (StringUtils.hasText(brandName)) {
      boolQueryBuilder.filter(q -> q.term(t -> t
          .field("brand_name")
          .value(brandName)
      ));
    }

    Query query = boolQueryBuilder.build()._toQuery();

    NativeQuery searchQuery = NativeQuery.builder()
        .withQuery(query)
        .withPageable(pageable)
        .build();

    SearchHits<SearchProduct> searchHits = elasticsearchOperations.search(
        searchQuery,
        SearchProduct.class
    );

    List<SearchProduct> products = searchHits.stream()
        .map(SearchHit::getContent)
        .toList();

    return new PageImpl<>(products, pageable, searchHits.getTotalHits());
  }
}