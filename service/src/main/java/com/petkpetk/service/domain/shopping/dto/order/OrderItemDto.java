package com.petkpetk.service.domain.shopping.dto.order;

import java.time.LocalDateTime;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	private Long orderItemId;
	private Long payId;
	private Long orderId;
	private Long userId;
	private Long productId;
	private OrderStatus orderStatus;
	private LocalDateTime startTime;
	private LocalDateTime cancelTime;

}
