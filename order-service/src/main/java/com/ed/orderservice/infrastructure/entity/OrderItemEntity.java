package com.ed.orderservice.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ED_ORDER_ITEMS")
@Getter
@NoArgsConstructor
public class OrderItemEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ITEM_ID")
	private Long orderItemId;

	@Column(
			name = "ORDER_DETAIL_UUID",
			updatable = false,
			nullable = false,
			columnDefinition = "VARCHAR(36)")
	private String orderItemPublicId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private OrderEntity orderEntity;

	@Column(name ="BRAND_ID", nullable = false)
	private String brandId;

	@Column(name = "PRODUCT_ID", nullable = false)
	private String productId;

	@Column(name = "PRODUCT_NAME", nullable = false)
	private String productName;

	@Column(name = "QUANTITY", nullable = false)
	private Long quantity;

	@Column(name = "UNIT_PRICE", nullable = false)
	private Long unitPrice;

	@Column(name = "SIZE", nullable = false)
	private String size;

	@Column(name = "PRODUCT_CATEGORY", nullable = false)
	private String productCategory;

	@Builder
	private OrderItemEntity(String orderItemPublicId, OrderEntity orderEntity, String brandId,
			String productId, String productName, Long quantity, Long unitPrice, String size,
			String productCategory) {
		this.orderItemPublicId = orderItemPublicId;
		this.orderEntity = orderEntity;
		this.brandId = brandId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.size = size;
		this.productCategory = productCategory;
	}

	void updateOrder(OrderEntity orderEntity) {
		this.orderEntity = orderEntity;
	}
}
