package com.petkpetk.service.domain.shopping.dto.cart;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.order.OrderItemDto;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.item.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartItemDto {
	@NotNull(message = "상품 아이디는 필수 입력 값 입니다")
	private Long id;
	@Min(value = 1, message = "최소 1개 이상 담아주세요")
	private Long itemCount;
	private Long cartId;
	private Long itemId;



	public CartItem toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, CartItem.class);
	}
	public static CartItemDto fromEntity(CartItem cartItem) {
		return EntityAndDtoConverter.convertToDto(cartItem, CartItemDto.class);
	}



	public static CartItemDto of( Long id,Long itemCount, Long cartId, Long itemId) {
		return new CartItemDto(
			id,
			itemCount,
			cartId,
			itemId
		);
	}

	}


