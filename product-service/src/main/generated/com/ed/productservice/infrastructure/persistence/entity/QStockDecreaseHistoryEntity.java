package com.ed.productservice.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStockDecreaseHistoryEntity is a Querydsl query type for StockDecreaseHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStockDecreaseHistoryEntity extends EntityPathBase<StockDecreaseHistoryEntity> {

    private static final long serialVersionUID = 991167450L;

    public static final QStockDecreaseHistoryEntity stockDecreaseHistoryEntity = new QStockDecreaseHistoryEntity("stockDecreaseHistoryEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final NumberPath<Long> createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final NumberPath<Long> deletedBy = _super.deletedBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final EnumPath<com.ed.productservice.domain.vo.ProductCategory> productCategory = createEnum("productCategory", com.ed.productservice.domain.vo.ProductCategory.class);

    public final StringPath productPublicId = createString("productPublicId");

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public final StringPath size = createString("size");

    public final EnumPath<com.ed.productservice.domain.vo.StockDecreaseHistoryStatus> status = createEnum("status", com.ed.productservice.domain.vo.StockDecreaseHistoryStatus.class);

    public final StringPath transactionId = createString("transactionId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final NumberPath<Long> updatedBy = _super.updatedBy;

    public QStockDecreaseHistoryEntity(String variable) {
        super(StockDecreaseHistoryEntity.class, forVariable(variable));
    }

    public QStockDecreaseHistoryEntity(Path<? extends StockDecreaseHistoryEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStockDecreaseHistoryEntity(PathMetadata metadata) {
        super(StockDecreaseHistoryEntity.class, metadata);
    }

}

