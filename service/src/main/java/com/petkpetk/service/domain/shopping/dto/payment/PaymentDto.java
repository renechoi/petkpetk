package com.petkpetk.service.domain.shopping.dto.payment;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.shopping.dto.delivery.DeliveryDto;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.entity.payment.Payment;
import com.petkpetk.service.domain.shopping.service.delivery.DeliveryService;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

	private Long id;
	private Long orderId;

	private Long userId;

	private Long productId;
	private Long totalCost;
	private String payToken; // *****
	private String payApp; // *****

	public Payment toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Payment.class);
	}
	public static PaymentDto fromEntity(Payment payment) {
		return EntityAndDtoConverter.convertToDto(payment, PaymentDto.class);
	}



	public static PaymentDto of(Long id, Long orderId, Long userId, Long productId, Long totalCost, String payToken,
		String payApp) {
		return new PaymentDto(
			id,
			orderId,
			userId,
			productId,
			totalCost,
			payToken,
			payApp
		);
	}


}
