package com.petkpetk.service.domain.shopping.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.petkpetk.service.domain.shopping.dto.cart.CartDetailDto;
import com.petkpetk.service.domain.shopping.dto.cart.request.CartItemRequest;
import com.petkpetk.service.domain.shopping.dto.cart.response.CartItemResponse;
import com.petkpetk.service.domain.shopping.dto.order.CheckoutDto;
import com.petkpetk.service.domain.shopping.dto.order.request.CheckoutRequest;
import com.petkpetk.service.domain.shopping.exception.OutOfStockException;
import com.petkpetk.service.domain.shopping.exception.StockAlreadyInCartException;
import com.petkpetk.service.domain.shopping.service.cart.CartService;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;


	@GetMapping("")
	public String cartItems(@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal, Model model) {
		CartItemResponse cartItemResponse = cartService.getCartItems(userAccountPrincipal.getEmail());
		model.addAttribute("order",new CheckoutRequest(cartItemResponse));
		model.addAttribute("cartItems", cartItemResponse);
		return "cart/cart";
	}

	@PostMapping(value = "")
	@ResponseBody
	public ResponseEntity<?> cartItems(@RequestBody  CartItemRequest cartItemRequest,
		@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
		if (userAccountPrincipal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			cartService.addCartItems(cartItemRequest, userAccountPrincipal.getEmail());
			return ResponseEntity.ok().body(true);
		} catch (OutOfStockException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (StockAlreadyInCartException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (Exception e){
			System.out.println("e = " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("잠시 후 다시 시도해주세요.");
		}
	}








	// @PatchMapping(value = "/cartItem/{cartItemId}")
	// public @ResponseBody ResponseEntity<?> updateCartItem(@PathVariable("cartItemId") Long cartItemId, Long count,
	// 	@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
	// 	if (count <= 0) {
	// 		return new ResponseEntity<>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
	// 	} else if (!cartService.validateCartItem(cartItemId, userAccountPrincipal.getName())) {
	// 		return forbiddenResponse("수정 권한이 없습니다.");
	// 	}
	//
	// 	cartService.updateCartItemCount(cartItemId, count);
	// 	return okResponse(cartItemId);
	// }
	//
	// @DeleteMapping(value = "/cartItem/{cartItemId}")
	// public @ResponseBody ResponseEntity<?> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
	// 	@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
	// 	if (!cartService.validateCartItem(cartItemId, userAccountPrincipal.getName())) {
	// 		return forbiddenResponse("삭제 권한이 없습니다.");
	// 	}
	// 	cartService.deleteCartItem(cartItemId);
	// 	return okResponse(cartItemId);
	// }
	//
	// @PostMapping("/cart/orders")
	// public ResponseEntity<?> orderCartItem(@RequestBody CartOrderDto cartOrderDto,
	// 	@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {
	// 	List<CartOrderDto> cartOrderDtos = cartOrderDto.getCartOrderDtos();
	// 	if (cartOrderDtos == null || cartOrderDtos.isEmpty()) {
	// 		return forbiddenResponse("주문할 상품을 선택해주세요");
	// 	}
	//
	// 	boolean hasInvalidCartItems = cartOrderDtos.stream()
	// 		.anyMatch(
	// 			cartOrder -> !cartService.validateCartItem(cartOrder.getCartItemId(), userAccountPrincipal.getName()));
	//
	// 	return hasInvalidCartItems ? forbiddenResponse("주문 권한이 없습니다.") :
	// 		okResponse(cartService.orderCartItem(cartOrderDtos, userAccountPrincipal.getName()));
	// }




	private ResponseEntity<?> okResponse(Object body) {
		return ResponseEntity.ok().body(body);
	}

	private ResponseEntity<?> forbiddenResponse(String message) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
	}

}
