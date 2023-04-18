package com.petkpetk.service.domain.shopping.dto.delivery;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.cart.CartDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.service.delivery.DeliveryService;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {

	private Long id;
	private Long pay_id;

	private UserAccount userAccount;
	private Order order;

	private DeliveryService deliveryService;


	public Delivery toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Delivery.class);
	}
	public static DeliveryDto fromEntity(Delivery delivery) {
		return EntityAndDtoConverter.convertToDto(delivery, DeliveryDto.class);
	}



	public static DeliveryDto of(Long id, Long pay_id, UserAccount userAccount, Order order, DeliveryService deliveryService) {
		return new DeliveryDto(
			id,
			pay_id,
			userAccount,
			order,
			deliveryService
		);
	}



}
