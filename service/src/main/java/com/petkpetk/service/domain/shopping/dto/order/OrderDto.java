package com.petkpetk.service.domain.shopping.dto.order;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.entity.order.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	private Long id;

	private Long userId;
	private Long productId;
	private Long amount;
	private double salePercent;
	private Long salePrice;

	public Order toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Order.class);
	}
	public static OrderDto fromEntity(Order order) {
		return EntityAndDtoConverter.convertToDto(order, OrderDto.class);
	}

	public OrderDto(Long id, Long userId, Long productId, Long amount, double salePercent) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.amount = amount;
		this.salePercent = salePercent;
	}

	public OrderDto(Long id, Long userId, Long productId, Long amount, Long salePrice) {
		this.id = id;
		this.userId = userId;
		this.productId = productId;
		this.amount = amount;
		this.salePrice = salePrice;
	}

	public static OrderDto of(Long id, Long userId, Long productId, Long amount, double salePercent) {
		return new OrderDto(
			id,
			userId,
			productId,
			amount,
			salePercent
		);
	}

	public static OrderDto of(Long id, Long userId, Long productId, Long amount, Long salePrice) {
		return new OrderDto(
			id,
			userId,
			productId,
			amount,
			salePrice
		);
	}


}
