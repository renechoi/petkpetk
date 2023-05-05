package com.petkpetk.service.domain.shopping.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.shopping.dto.order.OrderHistoryDto;
import com.petkpetk.service.domain.shopping.dto.order.request.CheckoutReqeust;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.shopping.service.order.OrderService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {

	private final OrderService orderService;

	@PostMapping("")
	public String checkout(@Valid CheckoutReqeust checkoutReqeust, Model model){
		//todo : item 찾아오기
		model.addAttribute("item", orderService.createCheckOut(checkoutReqeust));
		return "order/checkout";
	}



	
	
}
