package com.petkpetk.service.domain.shopping.service.delivery;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.delivery.DeliveryRepository;
import com.petkpetk.service.domain.shopping.repository.order.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
}
