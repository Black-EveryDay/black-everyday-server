package com.ed.productservice.infrastructure.persistence.entity.size;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBottomSizeStockEntity is a Querydsl query type for BottomSizeStockEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBottomSizeStockEntity extends EntityPathBase<BottomSizeStockEntity> {

    private static final long serialVersionUID = 743118711L;

    public static final QBottomSizeStockEntity bottomSizeStockEntity = new QBottomSizeStockEntity("bottomSizeStockEntity");

    public final com.ed.productservice.infrastructure.persistence.entity.QBaseEntity _super = new com.ed.productservice.infrastructure.persistence.entity.QBaseEntity(this);

    public final StringPath bottomSize = createString("bottomSize");

    public final NumberPath<Long> bottomSizeId = createNumber("bottomSizeId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> deletedBy = _super.deletedBy;

    public final NumberPath<java.math.BigDecimal> hipWidth = createNumber("hipWidth", java.math.BigDecimal.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final NumberPath<java.math.BigDecimal> thighCircumference = createNumber("thighCircumference", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> totalLength = createNumber("totalLength", java.math.BigDecimal.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QBottomSizeStockEntity(String variable) {
        super(BottomSizeStockEntity.class, forVariable(variable));
    }

    public QBottomSizeStockEntity(Path<? extends BottomSizeStockEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBottomSizeStockEntity(PathMetadata metadata) {
        super(BottomSizeStockEntity.class, metadata);
    }

}

