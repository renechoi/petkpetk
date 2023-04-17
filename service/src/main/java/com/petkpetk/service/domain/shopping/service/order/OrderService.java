package com.petkpetk.service.domain.shopping.service.order;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository repository;
/*
	private final ItemRepository itemRepository;
	private final UserAccountRepository userAccountRepository;
	private final ItemImageRepository itemImageRepository;
*/


}
