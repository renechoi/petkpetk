package com.petkpetk.service.domain.shopping.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.order.request.CheckoutRequest;
import com.petkpetk.service.domain.shopping.service.order.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

	private final OrderService orderService;

	@PostMapping("")
	public String checkout(@Valid CheckoutRequest checkoutRequest, Model model){
		model.addAttribute("item", orderService.createCheckOut(checkoutRequest));
		return "order/checkout";
	}

}
