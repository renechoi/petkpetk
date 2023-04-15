package com.petkpetk.service.domain.shopping.service.cart;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.cart.CartRepository;
import com.petkpetk.service.domain.shopping.service.order.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
	private final OrderService orderService;

	/*
	private final ItemRepository itemRepository;
	private final UserAccountRepository userAccountRepository;
	private final CartItemRepository cartItemRepository;
	 */

}
