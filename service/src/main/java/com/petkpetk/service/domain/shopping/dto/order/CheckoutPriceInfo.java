package com.petkpetk.service.domain.shopping.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutPriceInfo {
	private Long totalPrice;
	private Long deliveryPrice;
	private Long couponDiscount;
	private Long pointDiscount;
	private Long finalPaymentPrice;

}
