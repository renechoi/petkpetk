package com.petkpetk.service.domain.shopping.entity.delivery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long deliveryId;

	private Long payId;
	private Long userId;
	private Long orderId;
	private DeliveryStatus deliveryStatus;
}

