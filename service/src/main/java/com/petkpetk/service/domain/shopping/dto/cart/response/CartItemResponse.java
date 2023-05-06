package com.petkpetk.service.domain.shopping.dto.cart.response;

import java.util.LinkedHashSet;
import java.util.Set;

import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartPriceInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

	private Set<CartItemDto> items = new LinkedHashSet<>();
	private CartPriceInfo cartPriceInfo;

	public static CartItemResponse of() {
		return new CartItemResponse();
	}

	public static CartItemResponse of(Set<CartItemDto> cartItemDto, CartPriceInfo cartPriceInfo) {
		return new CartItemResponse(cartItemDto, cartPriceInfo);
	}


	public boolean isCartEmpty() {
		return this.items.isEmpty();
	}

	// public static CartItemResponse

}
