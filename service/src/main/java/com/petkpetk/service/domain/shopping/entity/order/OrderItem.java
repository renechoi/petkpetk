package com.petkpetk.service.domain.shopping.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.item.Item;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	@ToString.Exclude
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id")
	@ToString.Exclude
	private Order order;

	private Long orderPrice; // 주문가격

	private Long orderCount; // 주문수량

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	public OrderItem(Item item, Order order, Long orderPrice, Long orderCount, OrderStatus orderStatus) {
		this.item = item;
		this.order = order;
		this.orderPrice = orderPrice;
		this.orderCount = orderCount;
		this.orderStatus = orderStatus;
	}

	public static OrderItem of(Item item, Order order, Long orderPrice, Long orderCount, OrderStatus orderStatus) {
		return new OrderItem(item, order, orderPrice, orderCount, orderStatus);
	}

	public static OrderItem from(Item item, Long stockAmount) {
		OrderItem orderItem = OrderItem.of(item, null, item.getPrice(), stockAmount, null);
		item.removeStock(stockAmount);
		return orderItem;
	}

	/** 주문상품 전체 가격 조회 */
	public Long getTotalPrice() {
		return getOrderPrice() * getOrderCount();
	}

	public Long getTotalPrice(Item item) {
		return (long)((getOrderPrice() * getOrderCount()) / (item.getDiscountRate()));
	}

	/** 주문 취소 */
	public void cancel() {
		this.getItem().addStock(orderCount);
	}

	public static OrderItem createOrderItem(Item item, Long stockAmount, OrderStatus orderStatus) {
		OrderItem orderItem = OrderItem.of(item, null, item.getPrice(), stockAmount, OrderStatus.ORDER);
		System.out.println("orderItem = " + orderItem);
		item.removeStock(stockAmount);
		return orderItem;
	}

}


