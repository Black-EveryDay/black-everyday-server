package com.ed.productservice.infrastructure.persistence.repository;

import static com.ed.productservice.infrastructure.persistence.entity.QBrandEntity.brandEntity;
import static com.ed.productservice.infrastructure.persistence.entity.QProductEntity.productEntity;

import com.ed.productservice.domain.vo.ProductCategory;
import com.ed.productservice.domain.vo.ProductInfoDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;


@Repository
@RequiredArgsConstructor
public class ProductQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    public Page<ProductInfoDto> search(
        String productName,
        String color,
        ProductCategory category,
        Integer minPrice,
        Integer maxPrice,
        String brandName,
        Pageable pageable
    ) {
        JPAQuery<ProductInfoDto> query = queryFactory
            .select(Projections.constructor(ProductInfoDto.class,
                productEntity.productPublicId,
                productEntity.color,
                productEntity.image,
                productEntity.category,
                productEntity.description,
                productEntity.name,
                productEntity.price,
                brandEntity.brandName))
            .from(productEntity)
            .join(brandEntity).on(productEntity.brandId.eq(brandEntity.brandId))
            .where(
                nameContains(productName),
                colorEquals(color),
                categoryEquals(category),
                priceGoe(minPrice),
                priceLoe(maxPrice),
                brandNameEquals(brandName)
            );

        List<ProductInfoDto> results = query
            .orderBy(getOrderSpecifier(pageable))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = Optional.ofNullable(queryFactory
            .select(productEntity.count())
            .from(productEntity)
            .join(brandEntity).on(productEntity.brandId.eq(brandEntity.brandId))
            .where(
                nameContains(productName),
                colorEquals(color),
                categoryEquals(category),
                priceGoe(minPrice),
                priceLoe(maxPrice),
                brandNameEquals(brandName)
            )
            .fetchOne()).orElse(0L);

        return new PageImpl<>(results, pageable, total);
    }


    private BooleanExpression nameContains(String name) {
        return StringUtils.hasText(name) ? productEntity.name.contains(name) : null;
    }

    private BooleanExpression colorEquals(String color) {
        return StringUtils.hasText(color) ? productEntity.color.eq(color) : null;
    }

    private BooleanExpression categoryEquals(ProductCategory category) {
        return category != null ? productEntity.category.eq(category) : null;
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice != null ? productEntity.price.goe(minPrice) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ? productEntity.price.loe(maxPrice) : null;
    }

    private BooleanExpression brandNameEquals(String brandName) {
        return StringUtils.hasText(brandName) ? brandEntity.brandName.eq(brandName) : null;
    }

    private OrderSpecifier<?> getOrderSpecifier(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                switch (order.getProperty()) {
                    case "createdAt":
                        return order.isAscending() ?
                            productEntity.createdAt.asc() :
                            productEntity.createdAt.desc();
                    case "price":
                        return order.isAscending() ?
                            productEntity.price.asc() :
                            productEntity.price.desc();
                }
            }
        }
        return productEntity.createdAt.desc();
    }
}

