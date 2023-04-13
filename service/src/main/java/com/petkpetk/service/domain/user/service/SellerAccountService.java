package com.petkpetk.service.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.user.dto.SellerAccountDto;
import com.petkpetk.service.domain.user.entity.SellerAccount;
import com.petkpetk.service.domain.user.exception.UserDuplicateException;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.repository.SellerAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SellerAccountService {

	private final SellerAccountRepository sellerAccountRepository;

	public void save(SellerAccountDto sellerAccountDto) {

		if (isDuplicate(sellerAccountDto.getEmail())) {
			throw new UserDuplicateException();
		}

		SellerAccount sellerAccount = sellerAccountDto.toEntity();

		sellerAccountRepository.save(sellerAccount);
	}

	public void update(SellerAccountDto sellerAccountdto) {
		SellerAccount sellerAccount = findByEmail(sellerAccountdto).orElseThrow(UserNotFoundException::new);
		sellerAccount.update(sellerAccountdto);
	}

	public void delete(SellerAccountDto sellerAccountDto) {
		SellerAccount sellerAccount = findByEmail(sellerAccountDto).orElseThrow(UserNotFoundException::new);
		sellerAccount.setDeletedYn("Y");
	}

	public Optional<SellerAccountDto> searchSellerDto(String email) {
		return sellerAccountRepository.findByEmail(email).map(SellerAccountDto::fromEntity);
	}

	public Optional<SellerAccountDto> searchSellerDto(SellerAccountDto sellerAccountDto) {
		return findByEmail(sellerAccountDto).map(SellerAccountDto::fromEntity);
	}

	public Optional<SellerAccount> searchSeller(String email) {
		return sellerAccountRepository.findByEmail(email);
	}

	public Optional<SellerAccount> searchSeller(SellerAccountDto sellerAccountDto) {
		return findByEmail(sellerAccountDto);
	}

	private boolean isDuplicate(String email) {
		return sellerAccountRepository.findByEmail(email).isPresent();
	}

	private Optional<SellerAccount> findByEmail(SellerAccountDto sellerAccountDto) {
		return sellerAccountRepository.findByEmail(sellerAccountDto.getEmail());
	}
}

