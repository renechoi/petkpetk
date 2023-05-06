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
import com.petkpetk.service.domain.shopping.dto.order.request.OrderRequest;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.service.order.OrderService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

	@PostMapping("")
	public String order(@Valid OrderRequest orderRequest, Model model,
		@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal){

		//todo : 결제 기능 구현
		Long orderId = orderService.createOrder(orderRequest, userAccountPrincipal.getEmail());

		return "payment/complete";
	}


	@PostMapping("/{orderId}/cancel")
	public ResponseEntity cancelOrder(@PathVariable("orderId") Order order, Delivery delivery, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		if(!orderService.validateOrder(order.getId(), userAccountPrincipal.getName())){
			return new ResponseEntity("주문 취소 권한이 없습니다", HttpStatus.FORBIDDEN);
		}

		orderService.cancelOrder(order, delivery);
		return ResponseEntity.ok(order);
	}


	@GetMapping(value = {"/orders", "/orders/{page}"})
	public String orderHistory(@PathVariable("page") Optional<Integer> page, Authentication authentication, Model model){
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 4);

		Page<OrderHistoryDto> orderHistoryDtos = orderService.getOrders(authentication.getName(), pageRequest);

		model.addAttribute("orders", orderHistoryDtos);
		model.addAttribute("page", pageRequest.getPageNumber());
		model.addAttribute("maxPage", 5);

		return "order/user/orderList";
	}

}
