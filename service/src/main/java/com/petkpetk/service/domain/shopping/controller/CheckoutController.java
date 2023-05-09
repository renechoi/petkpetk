package com.petkpetk.service.domain.shopping.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.order.OrderDto;
import com.petkpetk.service.domain.shopping.dto.order.OrderItemDto;
import com.petkpetk.service.domain.shopping.dto.order.request.CheckoutRequest;
import com.petkpetk.service.domain.shopping.dto.order.response.CheckoutResponse;
import com.petkpetk.service.domain.shopping.dto.order.response.OrderCheckOutResponse;
import com.petkpetk.service.domain.shopping.dto.payment.PaymentRequest;
import com.petkpetk.service.domain.shopping.service.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

	private final OrderService orderService;

	@PostMapping()
	public String checkout(@Valid CheckoutRequest checkoutRequest, Model model){

		CheckoutResponse checkoutResponse = orderService.createCheckOut(checkoutRequest);
		model.addAttribute("item", checkoutResponse);
		model.addAttribute("payment", new PaymentRequest());


		// CheckoutRequest checkoutRequest = new CheckoutRequest(checkoutDto);
		// CheckoutResponse checkoutResponse = orderService.createCheckOut(checkoutRequest);
		// model.addAttribute("item", checkoutResponse);
		// model.addAttribute("checkoutPriceInfo", checkoutResponse.getCheckoutPriceInfo());
		// return "order/checkout";



		// 주문 정보
		// model.addAttribute("item", checkoutResponse);
		// // 결제 정보
		// model.addAttribute("payment", checkoutResponse.getCheckoutPriceInfo());
		//
		// // 배송 정보
		// model.addAttribute("delivery", checkoutResponse.getCheckoutPriceInfo().getDeliveryPrice());

		return "order/checkout";
	}

}
