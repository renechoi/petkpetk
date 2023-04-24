package com.petkpetk.service.domain.shopping.entity.delivery;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;
import com.petkpetk.service.domain.shopping.entity.cart.Cart;
import com.petkpetk.service.domain.shopping.entity.cart.CartItem;
import com.petkpetk.service.domain.shopping.entity.item.Item;
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
public class Delivery extends AuditingFields {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long id;

	private Long payId;

	@OneToMany(mappedBy = "user_account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private UserAccount userAccount;
	private Long orderId;
	private DeliveryStatus deliveryStatus;

	public Delivery(Long payId, UserAccount userAccount, Long orderId, DeliveryStatus deliveryStatus) {
		this.payId = payId;
		this.userAccount = userAccount;
		this.orderId = orderId;
		this.deliveryStatus = deliveryStatus;
	}

	public static Delivery of(Long payId, UserAccount userAccount, Long orderId, DeliveryStatus deliveryStatus) {
		return new Delivery(payId, userAccount, orderId, deliveryStatus);
	}

	public static Delivery createDelivery(Long payId, UserAccount userAccount, Long orderId, DeliveryStatus deliveryStatus){
		return Delivery.of(payId, userAccount, orderId, deliveryStatus);
	}


}

