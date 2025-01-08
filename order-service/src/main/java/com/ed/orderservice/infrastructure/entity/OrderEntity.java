package com.ed.orderservice.infrastructure.entity;

import com.ed.orderservice.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Entity
@Table(name = "ED_ORDERS")
@AllArgsConstructor
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORDER_ID")
	private Long orderId;

	@Column(name = "ORDER_PUBLIC_Id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	private String orderPublicId;

	@Enumerated(EnumType.STRING)
	@Column(name = "ORDER_STATE", nullable = false)
	private OrderStatus orderState;

	@Column(name = "ORDER_DATE", nullable = false)
	private LocalDateTime orderDate;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItemEntity> orderItemEntity = new ArrayList<>();

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderStateHistoryEntity> orderStateHistoryEntity = new ArrayList<>();

	@Column(name = "TOTAL_AMOUNT", nullable = false)
	private Long totalAmount;

	@Column(name = "TOTAL_QUANTITY", nullable = false)
	private Long totalQuantity;

}
