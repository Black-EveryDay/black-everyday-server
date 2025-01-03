package com.ed.productservice.infrastructure.persistence.entity;

import com.ed.productservice.domain.ProductForCreate;
import com.ed.productservice.domain.vo.Product;
import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@Table(name = "ed_product")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class ProductEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productPublicId;
    private Long brandId;
    private String name;
    private int price;
    private String description;
    private String color;
    private Integer quantity;
    private String image;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    public ProductEntity(String productPublicId, Long brandId, String name, int price, String description, String color, Integer quantity, String image, ProductStatus status, ProductCategory category) {
        this.productPublicId = productPublicId;
        this.brandId = brandId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.color = color;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.category = category;
    }

    public static ProductEntity from(ProductForCreate productForCreate, Long brandId) {
        return ProductEntity.builder().
                productPublicId(createPublicId())
                .brandId(brandId)
                .name(productForCreate.getName())
                .price(productForCreate.getPrice())
                .description(productForCreate.getDescription())
                .color(productForCreate.getColor())
                .quantity(productForCreate.getQuantity())
                .image(productForCreate.getImage())
                .status(productForCreate.getStatus())
                .category(productForCreate.getCategory())
                .build();
    }

    private static String createPublicId() {
        return UUID.randomUUID().toString();
    }

    public Product toDomain() {
        return new Product(
                productId,
                productPublicId,
                brandId,
                name,
                price,
                description,
                color,
                quantity,
                image,
                status,
                category,
                getCreatedAt()
        );
    }
}
