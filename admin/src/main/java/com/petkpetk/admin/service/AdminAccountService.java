package com.petkpetk.admin.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.petkpetk.admin.dto.AdminAccountDto;
import com.petkpetk.admin.dto.request.AdminSignupRequest;
import com.petkpetk.admin.entity.AdminAccount;
import com.petkpetk.admin.repository.AdminAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminAccountService {

	private final AdminAccountRepository adminAccountRepository;

	private final PasswordEncoder passwordEncoder;
	public Optional<AdminAccountDto> searchAdminDto(String email) {
		return adminAccountRepository.findByEmail(email).map(AdminAccountDto::fromEntity);
	}

	public void save(AdminSignupRequest adminSignupRequest) {
		AdminAccount adminAccount = adminSignupRequest.toEntity();
		adminAccount.encodePassword(passwordEncoder);
		adminAccountRepository.save(adminAccount);
	}

	public boolean checkEmailDuplicate(String email) {
		return !adminAccountRepository.existsByEmail(email);
	}
}
