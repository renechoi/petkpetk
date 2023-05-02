package com.petkpetk.service.domain.shopping.controller;

import java.security.Principal;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/user/order") // 수정 예정
public class OrderController {

	private final OrderService orderService;

	@GetMapping("")
	public String order(Model model){
		model.addAttribute("orderRequest", new OrderRequest());
		return "order-test";
	}


	@PostMapping("")
	public ResponseEntity<Object> createOrder(@RequestBody @Valid  OrderRequest orderRequest,
								BindingResult bindingResult,
								@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal){
		// todo :
		//  principal -> userAccountDto 만들어서
		// item에 담아주고

		if(bindingResult.hasErrors()) {
			String errorMsg = bindingResult.getAllErrors().stream()
				.map(ObjectError::getDefaultMessage)
				.collect(Collectors.joining(", "));
			return ResponseEntity.badRequest().body(errorMsg);

		}
		try {
		Long orderId = orderService.createOrder(orderRequest, userAccountPrincipal.toDto().getEmail());
			return ResponseEntity.ok(orderId);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}



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
	public String orderHistory(@PathVariable("page") Optional<Integer> page, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal, Model model){
		PageRequest pageRequest = PageRequest.of(page.orElse(0), 4);

		Page<OrderHistoryDto> orderHistoryDtos = orderService.getOrders(userAccountPrincipal.getName(), pageRequest);

		model.addAttribute("orders", orderHistoryDtos);
		model.addAttribute("page", pageRequest.getPageNumber());
		model.addAttribute("maxPage", 5);

		return "order/orderHist";
	}
	
	
}
