package com.ed.productservice.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBrandEntity is a Querydsl query type for BrandEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrandEntity extends EntityPathBase<BrandEntity> {

    private static final long serialVersionUID = 553570163L;

    public static final QBrandEntity brandEntity = new QBrandEntity("brandEntity");

    public final StringPath brandAddress = createString("brandAddress");

    public final NumberPath<Long> brandId = createNumber("brandId", Long.class);

    public final StringPath brandName = createString("brandName");

    public final EnumPath<com.ed.productservice.domain.vo.BrandType> brandType = createEnum("brandType", com.ed.productservice.domain.vo.BrandType.class);

    public QBrandEntity(String variable) {
        super(BrandEntity.class, forVariable(variable));
    }

    public QBrandEntity(Path<? extends BrandEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBrandEntity(PathMetadata metadata) {
        super(BrandEntity.class, metadata);
    }

}

