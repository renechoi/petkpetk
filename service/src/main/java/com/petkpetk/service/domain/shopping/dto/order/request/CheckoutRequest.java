package com.petkpetk.service.domain.shopping.dto.order.request;

import java.util.List;
import java.util.stream.Collectors;

import com.petkpetk.service.domain.shopping.dto.priceInfo.ItemPriceInfo;
import com.petkpetk.service.domain.shopping.dto.cart.response.CartItemResponse;
import com.petkpetk.service.domain.shopping.dto.order.CheckoutDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutRequest {

	private List<CheckoutDto> checkoutDtos;
	// private CheckoutDto checkoutDto;
	private ItemPriceInfo itemPriceInfo = new ItemPriceInfo();


	public CheckoutRequest(List<CheckoutDto> checkoutDtos) {
		this.checkoutDtos = checkoutDtos;
	}

	public CheckoutRequest(CartItemResponse cartItemResponse){
		this.checkoutDtos = cartItemResponse.getItems().stream()
			.map(cartItemDto -> CheckoutDto.of(cartItemDto.getId(), cartItemDto.getCartItemCount()))
			.collect(Collectors.toList());
		this.itemPriceInfo = cartItemResponse.getItemPriceInfo();
	}

	// // totalPrice 가 50000원 이상이면 무료배송을 적용하도록 하고, 그렇지 않으면 2500원의 배송비를 부과
	// public Long getDeliveryPrice() {
	// 	if (totalPrice >= 50000) {
	// 		return 0L;
	// 	} else {
	// 		return 2500L;
	// 	}
	// }

	// public Long getPointDiscount() {
	// }
}
