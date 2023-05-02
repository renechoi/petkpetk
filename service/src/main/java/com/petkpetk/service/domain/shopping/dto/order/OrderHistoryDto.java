package com.petkpetk.service.domain.shopping.dto.order;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Enumerated;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.order.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderHistoryDto {

	private Long orderId;
	@Enumerated
	private OrderStatus orderStatus;

	private List<OrderItemDto> orderItemDtos = new ArrayList<>();

	public OrderHistoryDto(Order order) {
		this.orderId = order.getId();
		this.orderStatus = order.getOrderStatus();
	}

	public OrderHistoryDto(Order order, List<OrderItemDto> orderItemDtos) {
		this.orderId = order.getId();
		this.orderStatus = order.getOrderStatus();
		this.orderItemDtos = orderItemDtos;
	}

	public void addOrderItemDto(OrderItemDto orderItemDto){
		orderItemDtos.add(orderItemDto);
	}


}
