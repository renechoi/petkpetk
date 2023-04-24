package com.petkpetk.service.domain.shopping.entity.order;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.catalina.User;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class OrderItem extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private Long id;
	private Long payId;
	private Long orderId;

	private Long itemId;
	private OrderStatus orderStatus;
	private LocalDateTime startTime;
	private LocalDateTime cancelTime;

	public OrderItem(Long payId, Long orderId, Long itemId, OrderStatus orderStatus) {
		this.payId = payId;
		this.orderId = orderId;
		this.itemId = itemId;
		this.orderStatus = orderStatus;

	}

	public static OrderItem of(Long payId, Long orderId, Long itemId, OrderStatus orderStatus) {
		return new OrderItem(payId, orderId, itemId, orderStatus);
	}
}
