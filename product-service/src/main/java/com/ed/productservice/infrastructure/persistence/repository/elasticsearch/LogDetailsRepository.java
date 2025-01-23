package com.ed.productservice.infrastructure.persistence.repository.elasticsearch;

import com.ed.productservice.infrastructure.persistence.elasticsearch.LogDetails;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface LogDetailsRepository extends ElasticsearchRepository<LogDetails, String> {
  @Query("{\"bool\": {\"must\": [{\"range\": {\"@timestamp\": {\"gte\": \"?0\", \"lte\": \"?1\"}}}]}}")
  List<LogDetails> findByTimestampBetween(String startTime, String endTime);
}
