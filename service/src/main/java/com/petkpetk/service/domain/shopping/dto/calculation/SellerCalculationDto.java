package com.petkpetk.service.domain.shopping.dto.calculation;

import com.petkpetk.service.domain.shopping.dto.cart.CartDto;
import com.petkpetk.service.domain.user.entity.SellerAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerCalculationDto {

	private Long id;
	private SellerAccount sellerAccount;

	public SellerCalculationDto(SellerAccount sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public static CartDto of( Long userId, Long totalPrice) {
		return new CartDto(
			userId,
			totalPrice
		);
	}
}
