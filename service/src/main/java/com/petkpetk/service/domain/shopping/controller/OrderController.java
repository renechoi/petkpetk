package com.petkpetk.service.domain.shopping.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import com.petkpetk.service.domain.shopping.dto.order.request.OrderRequest;
import com.petkpetk.service.domain.shopping.service.order.OrderService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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



	// @DeleteMapping("")
	// public String cancelOrder() {
	//
	// 	return "";
	// }
	
	
}
