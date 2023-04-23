package com.petkpetk.service.domain.shopping.dto.cart;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {

	private Long cartItemId;
	private Long cartId;
	private Long itemId;

	public Cart toCart() {
		return EntityAndDtoConverter.convertToEntity(this, Cart.class);
	}


	public static CartDto of(Long cartItemId, Long cartId, Long itemId) {
		return new CartDto(
			cartItemId,
			cartId,
			itemId
		);
	}

}
