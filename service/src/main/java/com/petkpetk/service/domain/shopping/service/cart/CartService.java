package com.petkpetk.service.domain.shopping.service.cart;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.cart.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartRepository cartRepository;
}
