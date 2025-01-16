package com.ed.productservice.infrastructure.persistence.elasticsearch;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchProduct {
  @Id
  private String id;

  @Field(name = "product_public_id", type = FieldType.Keyword)
  private String productPublicId;

  @Field(name = "product_id", type = FieldType.Long)
  private Long productId;

  @Field(name = "brand_id", type = FieldType.Long)
  private Long brandId;

  @Field(name = "brand_name", type = FieldType.Keyword)
  private String brandName;

  @Field(name = "name", type = FieldType.Text)
  private String name;

  @Field(name = "description", type = FieldType.Text)
  private String description;

  @Field(name = "color", type = FieldType.Keyword)
  private String color;

  @Field(name = "image", type = FieldType.Keyword)
  private String image;

  @Field(name = "category", type = FieldType.Keyword)
  private String category;

  @Field(name = "price", type = FieldType.Long)
  private Long price;

  @Field(name = "created_at", type = FieldType.Date)
  private LocalDateTime createdAt;

  @Field(name = "is_deleted", type = FieldType.Boolean)
  private boolean isDeleted;
}
