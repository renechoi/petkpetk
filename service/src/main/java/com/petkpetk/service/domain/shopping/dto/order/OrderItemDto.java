package com.petkpetk.service.domain.shopping.dto.order;

import java.time.LocalDateTime;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.entity.order.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	private Long id;
	private Long payId;
	private Long orderId;
	private Long userId;
	private Long productId;
	private OrderStatus orderStatus;
	private LocalDateTime startTime;
	private LocalDateTime cancelTime;

	public OrderItem toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, OrderItem.class);
	}

	public static OrderItemDto fromEntity(Order order) {
		return EntityAndDtoConverter.convertToDto(order, OrderItemDto.class);
	}

	public static OrderItemDto of(Long id, Long payId, Long orderId, Long userId, Long productId,
		OrderStatus orderStatus,
		LocalDateTime startTime, LocalDateTime cancelTime) {
		return new OrderItemDto(
			id,
			payId,
			orderId,
			userId,
			productId,
			orderStatus,
			startTime,
			cancelTime
		);

	}
}
