package com.petkpetk.service.domain.shopping.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartItemDto;
import com.petkpetk.service.domain.shopping.dto.cart.CartOrderDto;
import com.petkpetk.service.domain.shopping.service.cart.CartService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

	@Autowired CartService cartService;

	@PostMapping(value = "/cart")
	public ResponseEntity<?> addCartItem(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		if (bindingResult.hasErrors()) {
			return badRequestResponse(bindingResult);
		}

		Long cartItemId;

		try {
			cartItemId = cartService.addCartItems(cartItemDto, userAccountPrincipal.getName());
		} catch (Exception e) {
			return badRequestResponse(e.getMessage());
		}
		return okResponse(cartItemId);
	}

	@GetMapping("/cart")
	public String getCartItems(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal, Model model) {
		List<CartDetailDto> cartItems = cartService.getCartItems(userAccountPrincipal.getName());

		model.addAttribute("cartItems", cartItems);
		return "cart/cartList";
	}

	@PatchMapping(value = "/cartItem/{cartItemId}")
	public @ResponseBody ResponseEntity<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, Long count, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		if (count <= 0) {
			return new ResponseEntity<>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
		} else if (!cartService.validateCartItem(cartItemId, userAccountPrincipal.getName())) {
			return forbiddenResponse("수정 권한이 없습니다.");
		}

		cartService.updateCartItemCount(cartItemId, count);
		return okResponse(cartItemId);
	}


	@DeleteMapping(value = "/cartItem/{cartItemId}")
	public @ResponseBody ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,  @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		if (!cartService.validateCartItem(cartItemId, userAccountPrincipal.getName())) {
			return forbiddenResponse("삭제 권한이 없습니다.");
		}
		cartService.deleteCartItem(cartItemId);
		return okResponse(cartItemId);
	}

	@PostMapping("/cart/orders")
	public ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto, @AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		List<CartOrderDto> cartOrderDtos = cartOrderDto.getCartOrderDtos();
		if (cartOrderDtos == null || cartOrderDtos.isEmpty()) {
			return forbiddenResponse("주문할 상품을 선택해주세요");
		}

		boolean hasInvalidCartItems = cartOrderDtos.stream()
			.anyMatch(cartOrder -> !cartService.validateCartItem(cartOrder.getCartItemId(), userAccountPrincipal.getName()));

		return hasInvalidCartItems ? forbiddenResponse("주문 권한이 없습니다.") : okResponse(cartService.orderCartItem(cartOrderDtos, userAccountPrincipal.getName()));
	}

	private ResponseEntity<?> badRequestResponse(BindingResult bindingResult) {
		List<String> fieldErrors = bindingResult.getFieldErrors()
			.stream()
			.map(FieldError::getDefaultMessage)
			.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(fieldErrors);
	}

	private ResponseEntity<?> badRequestResponse(String message) {
		return ResponseEntity.badRequest().body(message);
	}

	private ResponseEntity<?> okResponse(Object body) {
		return ResponseEntity.ok().body(body);
	}

	private ResponseEntity<?> forbiddenResponse(String message) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
	}




}
