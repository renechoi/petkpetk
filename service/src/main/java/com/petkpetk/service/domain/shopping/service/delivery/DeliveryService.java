package com.petkpetk.service.domain.shopping.service.delivery;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.constant.DeliveryStatus;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.repository.delivery.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;

	public List<Delivery> getAllDeliveries() {
		return deliveryRepository.findAll();
	}

	public Optional<Delivery> getDeliveryById(Long id) {
		return deliveryRepository.findById(id);
	}

	public void updateDeliveryStatus(Long id, DeliveryStatus deliveryStatus) {
		Optional<Delivery> optionalDelivery = deliveryRepository.findById(id);

		optionalDelivery.ifPresent(delivery -> {
			delivery.setDeliveryStatus(deliveryStatus);
			deliveryRepository.save(delivery);
		});
	}

}
