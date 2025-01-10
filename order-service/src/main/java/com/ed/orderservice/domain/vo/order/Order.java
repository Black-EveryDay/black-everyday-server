package com.ed.orderservice.domain.vo.order;

import com.ed.orderservice.domain.enums.OrderStatus;
import com.ed.orderservice.infrastructure.entity.OrderItemEntity;
import com.ed.orderservice.infrastructure.entity.OrderStatusHistoryEntity;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Order {
	private static final Random RANDOM = new Random();
	private Long orderId = null;
	private String orderPublicId;
	private String orderName;
	private String phoneNumber;

	private List<OrderItem> orderItems = new ArrayList<>();
	private Long totalAmount = 0L;
	private Long totalQuantity = 0L;

	private OrderStatus orderStatus = OrderStatus.ORDER_CREATED;
	private List<OrderStatusHistoryEntity> orderStatusHistory = new ArrayList<>();

	private OrderDelivery orderDelivery;

	private String paymentId = null;
	private LocalDateTime paidAt;

	@Builder
	private Order(Long orderId, Orderer orderer,
			List<OrderItem> orderItems, OrderDelivery orderDelivery) {
		this.orderId = orderId;
		this.orderPublicId = generateOrderNumber();
		this.orderName = orderer.getName();
		this.phoneNumber = orderer.getPhoneNumber();
		this.orderItems = orderItems;
		this.orderDelivery = orderDelivery;
		this.paymentId = null;
		this.paidAt = null;
		recalculateTotals();
	}

	public static OrderItemEntity fromOrderItem(OrderItem orderItem) {
		return OrderItemEntity.builder()
				.orderItemPublicId(orderItem.getOrderItemPublicId())
				.brandId(orderItem.getBrandId())
				.productId(orderItem.getProductId())
				.productName(orderItem.getProductName())
				.quantity(orderItem.getQuantity())
				.unitPrice(orderItem.getUnitPrice())
				.size(orderItem.getSize())
				.productCategory(orderItem.getProductCategory())
				.build();
	}

	public void updatePaymentInfo(String paymentId, LocalDateTime paidAt){
		this.paymentId = paymentId;
		this.paidAt = paidAt;
	}

	private String generateOrderNumber() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String datePart = dateFormat.format(new Date());
		int randomNumber = RANDOM.nextInt(1000000000);
		String randomPart = String.format("%010d", randomNumber);
		return datePart + randomPart;
	}

	private void recalculateTotals() {
		this.totalQuantity = orderItems.stream().mapToLong(OrderItem::getQuantity).sum();
		this.totalAmount = orderItems.stream()
				.mapToLong(detail -> detail.getQuantity() * detail.getUnitPrice())
				.sum();
	}
}
