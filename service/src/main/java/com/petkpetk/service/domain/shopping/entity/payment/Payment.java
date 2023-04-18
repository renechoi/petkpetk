package com.petkpetk.service.domain.shopping.entity.payment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.order.OrderItem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payment extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private Long id;
	@Column(unique = true)
	private Long orderId;
	@Column(unique = true)
	private Long userId;
	@Column(unique = true)
	private Long productId;
	private Long totalCost;
	private String payToken; // ***** 결제토큰
	private String payApp; // ***** 결제 방식

	public Payment(Long orderId, Long userId, Long productId, Long totalCost, String payToken, String payApp) {
		this.orderId = orderId;
		this.userId = userId;
		this.productId = productId;
		this.totalCost = totalCost;
		this.payToken = payToken;
		this.payApp = payApp;
	}

	public static Payment of(Long orderId, Long userId, Long productId, Long totalCost, String payToken, String payApp) {
		return new Payment(orderId, userId, productId, totalCost,payToken,payApp);
	}


}
