package com.petkpetk.service.domain.shopping.dto.order.request;

import java.util.List;

import com.petkpetk.service.domain.shopping.dto.order.CheckoutDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckoutRequest {

	private List<CheckoutDto> checkoutDtos;

	public CheckoutRequest(List<CheckoutDto> checkoutDtos) {
		this.checkoutDtos = checkoutDtos;
	}
}
