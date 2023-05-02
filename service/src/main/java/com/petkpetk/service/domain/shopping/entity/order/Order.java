package com.petkpetk.service.domain.shopping.entity.order;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@DynamicUpdate
@Where(clause = "deletedYn=N")
public class Order extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	@ToString.Exclude
	private UserAccount userAccount;


	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<OrderItem> orderItems = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "DELIVERY_ID")
	private Delivery delivery;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;



	private Long amount;
	private double salePercent;
	private Long salePrice;

	public Order(UserAccount userAccount, List<OrderItem> orderItems, Long amount, double salePercent) {
		this.userAccount = userAccount;
		this.orderItems = orderItems;
		this.amount = amount;
		this.salePercent = salePercent;
	}

	public Order(UserAccount userAccount, List<OrderItem> orderItems, Long amount, Long salePrice) {
		this.userAccount = userAccount;
		this.orderItems = orderItems;
		this.amount = amount;
		this.salePrice = salePrice;
	}

	public static Order of(UserAccount userAccount, List<OrderItem> orderItems, Long amount, double salePercent) {
		return new Order(userAccount, orderItems, amount, salePercent);
	}

	public static Order of(UserAccount userAccount, List<OrderItem> orderItems, Long amount, Long salePrice) {
		return new Order(userAccount, orderItems, amount, salePrice);
	}

	public Order(UserAccount userAccount, OrderStatus orderStatus, List<OrderItem> orderItems) {
		this.userAccount = userAccount;
		this.orderStatus = orderStatus;
		this.orderItems = orderItems;
	}


	public void addOrderItem(OrderItem orderItem){
		orderItems.add(orderItem);
		orderItem.setOrder(this);
	}

	public static Order from(UserAccount userAccount, List<OrderItem> orderItems){
		Order order = new Order();
		order.setUserAccount(userAccount);
		order.setOrderStatus(OrderStatus.ORDER);

		orderItems.forEach(order::addOrderItem);
		return order;

	}

	public void createDelivery(Delivery delivery) {
		this.delivery = delivery;
		delivery.setOrder(this);
	}

	public static Order from(UserAccount userAccount, List<OrderItem> orderItems, OrderStatus orderStatus, Delivery delivery){
		Order order = new Order();
		order.setUserAccount(userAccount);
		order.setOrderStatus(OrderStatus.ORDER);
		order.createDelivery(Delivery.of(userAccount));

		orderItems.forEach(order::addOrderItem);
		return order;
	}


	public Long getTotalPrice(){
		return orderItems.stream().mapToLong(OrderItem::getTotalPrice).sum();

	}



	// public void cancelOrder(){
	// 	if (delivery.getDeliveryStatus() == DeliveryStatus.DELIVERY_COMPLETED) {
	// 		throw new RuntimeException("이미 배송완료된 상품은 취소가 불가능합니다.");
	// 	}
	// 	this.orderStatus = OrderStatus.CANCEL;
	// 	orderItems.forEach(OrderItem::cancel);
	//
	// }

	public void cancel() {
		this.orderStatus = OrderStatus.CANCEL;
		this.deletedYn = "Y";
	}

	public boolean isDeliveryInProcess(){
		return delivery.getDeliveryStatus() == DeliveryStatus.DELIVERING || delivery.getDeliveryStatus() == DeliveryStatus.DELIVERY_COMPLETED;
	}
}
