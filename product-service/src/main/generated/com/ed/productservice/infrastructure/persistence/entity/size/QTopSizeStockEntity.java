package com.ed.productservice.infrastructure.persistence.entity.size;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTopSizeStockEntity is a Querydsl query type for TopSizeStockEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTopSizeStockEntity extends EntityPathBase<TopSizeStockEntity> {

    private static final long serialVersionUID = -1228523335L;

    public static final QTopSizeStockEntity topSizeStockEntity = new QTopSizeStockEntity("topSizeStockEntity");

    public final com.ed.productservice.infrastructure.persistence.entity.QBaseEntity _super = new com.ed.productservice.infrastructure.persistence.entity.QBaseEntity(this);

    public final NumberPath<java.math.BigDecimal> chestWidth = createNumber("chestWidth", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> deletedBy = _super.deletedBy;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<java.math.BigDecimal> shoulderWidth = createNumber("shoulderWidth", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> sleeveLength = createNumber("sleeveLength", java.math.BigDecimal.class);

    public final StringPath topSize = createString("topSize");

    public final NumberPath<Long> topSizeId = createNumber("topSizeId", Long.class);

    public final NumberPath<java.math.BigDecimal> totalLength = createNumber("totalLength", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QTopSizeStockEntity(String variable) {
        super(TopSizeStockEntity.class, forVariable(variable));
    }

    public QTopSizeStockEntity(Path<? extends TopSizeStockEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTopSizeStockEntity(PathMetadata metadata) {
        super(TopSizeStockEntity.class, metadata);
    }

}

