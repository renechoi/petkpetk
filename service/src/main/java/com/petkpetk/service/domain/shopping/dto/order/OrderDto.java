package com.petkpetk.service.domain.shopping.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

	private Long orderId;

	private Long userId;
	private Long productId;
	private Long amount;
	private double salePercent;
	private Long salePrice;
}
