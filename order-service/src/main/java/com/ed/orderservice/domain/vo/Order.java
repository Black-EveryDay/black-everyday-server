package com.ed.orderservice.domain.vo;

import com.ed.orderservice.domain.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Order {

	private String orderPublic;
	private OrderStatus orderStatus;
	private LocalDateTime orderDate;
	private List<OrderItem> orderItems = new ArrayList<>();
	private List<OrderStatusHistory> orderStatusHistory = new ArrayList<>();
	private Long totalAmount = 0L;
	private Long totalQuantity = 0L;


}
