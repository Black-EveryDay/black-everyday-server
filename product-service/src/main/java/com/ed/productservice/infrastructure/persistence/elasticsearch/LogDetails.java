package com.ed.productservice.infrastructure.persistence.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "springboot-elk")
public class LogDetails {

  @Id
  private String id;

  @Field(type = FieldType.Date)
  private String timestamp;

  @Field(type = FieldType.Keyword)
  private String message;
}
