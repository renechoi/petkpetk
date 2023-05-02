package com.petkpetk.service.domain.shopping.entity.delivery;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.entity.embedded.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;

	private Long payId;
	private Long userId;
	private Long orderId;

	@OneToOne(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Order order;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;

	/** 배송지*/
	@Embedded
	private Address address;

	public Delivery(Long payId, Long userId, Long orderId, DeliveryStatus deliveryStatus, Address address) {
		this.payId = payId;
		this.userId = userId;
		this.orderId = orderId;
		this.deliveryStatus = deliveryStatus;
		this.address = address;
	}

	public Delivery(Address address) {
		this.address = address;
	}

	public static Delivery of(UserAccount userAccount){
		return new Delivery(userAccount.getAddress());
	}

	public static Delivery of(Long payId, Long userId, Long orderId, DeliveryStatus deliveryStatus, Address address) {
		return new Delivery(payId, userId, orderId, deliveryStatus,address);
	}

	public void cancelDelivery(){
		this.deliveryStatus = DeliveryStatus.CANCEL;
	}

}

