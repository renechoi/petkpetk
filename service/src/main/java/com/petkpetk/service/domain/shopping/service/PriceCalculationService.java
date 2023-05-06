package com.petkpetk.service.domain.shopping.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartPriceInfo;
import com.petkpetk.service.domain.shopping.dto.order.CheckoutPriceInfo;

@Service
public class PriceCalculationService {

	public CartPriceInfo createCartPriceInfo(Set<CartItemDto> cartItemDtos, Long deliveryPrice,
		Long totalDiscountPrice) {

		Long totalPrice = calculateTotalPrice(cartItemDtos);

		Long paymentPrice = calculatePaymentPrice(totalPrice, deliveryPrice, totalDiscountPrice);
		return new CartPriceInfo(totalPrice, deliveryPrice, totalDiscountPrice, paymentPrice);
	}

	private Long calculateTotalPrice(Set<CartItemDto> cartItemDtos) {
		return cartItemDtos.stream()
			.mapToLong(CartItemDto::getPrice)
			.sum();
	}

	private Long calculatePaymentPrice(Long totalPrice, Long deliveryPrice, Long totalDiscountPrice) {
		return totalPrice + deliveryPrice - totalDiscountPrice;
	}

	public CheckoutPriceInfo createCheckoutPriceInfo(CartPriceInfo cartPriceInfo, Long couponDiscount,
		Long pointDiscount) {
		return new CheckoutPriceInfo(
			cartPriceInfo.getTotalPrice(),
			cartPriceInfo.getDeliveryPrice(),
			couponDiscount,
			pointDiscount,
			calculateFinalPaymentPrice(cartPriceInfo.getTotalPrice(), cartPriceInfo.getDeliveryPrice(), couponDiscount,
				pointDiscount)
		);
	}

	private Long calculateFinalPaymentPrice(Long totalPrice, Long deliveryPrice, Long couponDiscount,
		Long pointDiscount) {
		return totalPrice + deliveryPrice - couponDiscount - pointDiscount;
	}

}
