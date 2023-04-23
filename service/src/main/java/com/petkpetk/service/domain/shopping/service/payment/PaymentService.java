package com.petkpetk.service.domain.shopping.service.payment;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.shopping.repository.payment.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

	private final PaymentRepository paymentRepository;
}
