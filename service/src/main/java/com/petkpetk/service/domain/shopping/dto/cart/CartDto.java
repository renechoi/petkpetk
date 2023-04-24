package com.petkpetk.service.domain.shopping.dto.cart;

import java.util.List;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.order.OrderItemDto;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.user.dto.SellerAccountDto;
import com.petkpetk.service.domain.user.entity.SellerAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {


	private Long id;

	private Long userId;

	private Long totalPrice;


	public Cart toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Cart.class);
	}
	public static CartDto fromEntity(Cart cart) {
		return EntityAndDtoConverter.convertToDto(cart, CartDto.class);
	}

	public CartDto( Long id, Long totalPrice) {
		this.id = id;
		this.totalPrice = totalPrice;
	}

	public static CartDto of( Long id, Long totalPrice) {
		return new CartDto(
			id,
			totalPrice
		);
	}

}
