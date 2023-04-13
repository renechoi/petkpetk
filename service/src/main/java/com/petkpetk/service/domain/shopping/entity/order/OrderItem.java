package com.petkpetk.service.domain.shopping.entity.order;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class OrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderItemId;
	private Long payId;
	private Long orderId;
	private Long userId;
	private Long productId;
	private OrderStatus orderStatus;
	private LocalDateTime startTime;
	private LocalDateTime cancelTime;




}
