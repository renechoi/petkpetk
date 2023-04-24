package com.petkpetk.service.domain.shopping.service.delivery;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.delivery.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
}
