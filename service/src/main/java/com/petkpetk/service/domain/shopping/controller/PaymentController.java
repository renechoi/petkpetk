package com.petkpetk.service.domain.shopping.controller;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.order.request.OrderRequest;
import com.petkpetk.service.domain.shopping.service.order.OrderService;
import com.petkpetk.service.domain.shopping.service.payment.PaymentService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

	private final PaymentService paymentService;
	private final OrderService orderService;

	@PostMapping("")
	public String payment(@Valid OrderRequest orderRequest, Model model,
		@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal){
		//todo : 결제 기능 구현

		Long orderId = orderService.createOrder(orderRequest, userAccountPrincipal.getEmail());

		return "payment/complete";
	}
}
