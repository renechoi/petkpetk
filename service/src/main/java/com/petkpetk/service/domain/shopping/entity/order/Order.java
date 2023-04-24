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
import javax.persistence.Table;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "orders")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Order extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	@ToString.Exclude
	private UserAccount userAccount;


	@OneToMany(mappedBy = "order_item_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<OrderItem> orderItems = new ArrayList<>();

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



}
