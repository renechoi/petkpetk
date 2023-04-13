package com.petkpetk.service.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.exception.UserDuplicateException;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService {

	private final UserAccountRepository userAccountRepository;

	public void save(UserAccountDto userAccountDto) {

		if (isDuplicate(userAccountDto.getEmail())) {
			throw new UserDuplicateException();
		}

		UserAccount userAccount = userAccountDto.toEntity();

		userAccountRepository.save(userAccount);
	}

	public void update(UserAccountDto userAccountDto) {
		UserAccount userAccount = findByEmail(userAccountDto).orElseThrow(UserNotFoundException::new);
		userAccount.update(userAccountDto);
	}

	public void delete(UserAccountDto userAccountDto) {
		UserAccount userAccount = findByEmail(userAccountDto).orElseThrow(UserNotFoundException::new);
		userAccount.setDeletedYn("Y");
	}

	public Optional<UserAccountDto> searchUserDto(String email) {
		return userAccountRepository.findByEmail(email).map(UserAccountDto::fromEntity);
	}

	public Optional<UserAccountDto> searchUserDto(UserAccountDto userAccountDto) {
		return findByEmail(userAccountDto).map(UserAccountDto::fromEntity);
	}

	public Optional<UserAccount> searchUser(String email) {
		return userAccountRepository.findByEmail(email);
	}

	public Optional<UserAccount> searchUser(UserAccountDto userAccountDto) {
		return findByEmail(userAccountDto);
	}

	private boolean isDuplicate(String email) {
		return userAccountRepository.findByEmail(email).isPresent();
	}

	private Optional<UserAccount> findByEmail(UserAccountDto userAccountDto) {
		return userAccountRepository.findByEmail(userAccountDto.getEmail());
	}
}

