package com.petkpetk.service.domain.shopping.entity.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;

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
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	// @OneToMany(mappedBy = "order_item_id", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	// @ToString.Exclude
	// private List<OrderItem> orderItems = new ArrayList<>();

	private Long userId;

	private Long itemId;
	private Long amount;
	private double salePercent;
	private Long salePrice;

}
