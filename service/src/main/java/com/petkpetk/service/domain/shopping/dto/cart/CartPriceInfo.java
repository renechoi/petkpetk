package com.petkpetk.service.domain.shopping.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPriceInfo {
	private Long totalPrice;
	private Long deliveryPrice;
	private Long totalDiscountPrice;
	private Long paymentPrice;

}
