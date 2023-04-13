package com.petkpetk.service.domain.shopping.service.calculation;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.calculation.SellerCalculationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerCalculationService {

	private final SellerCalculationRepository sellerCalculationRepository;
}
