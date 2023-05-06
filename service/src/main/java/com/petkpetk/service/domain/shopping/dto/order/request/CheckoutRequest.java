package com.petkpetk.service.domain.shopping.dto.order.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartPriceInfo;
import com.petkpetk.service.domain.shopping.dto.cart.response.CartItemResponse;
import com.petkpetk.service.domain.shopping.dto.order.CheckoutDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutRequest {

	private List<CheckoutDto> checkoutDtos;
	private CartPriceInfo cartPriceInfo = new CartPriceInfo();

	public CheckoutRequest(List<CheckoutDto> checkoutDtos) {
		this.checkoutDtos = checkoutDtos;
	}

	public CheckoutRequest(CartItemResponse cartItemResponse){
		this.checkoutDtos = cartItemResponse.getItems().stream()
			.map(cartItemDto -> new CheckoutDto(cartItemDto.getId(), cartItemDto.getCartItemCount()))
			.collect(Collectors.toList());
		this.cartPriceInfo = cartItemResponse.getCartPriceInfo();
	}
}
