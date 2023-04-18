package com.petkpetk.service.domain.user.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
	private final PasswordEncoder passwordEncoder;

	public void save(UserAccountDto userAccountDto) {

		if (isDuplicate(userAccountDto.getEmail())) {
			throw new UserDuplicateException();
		}
		// TODO :
		UserAccount userAccount = userAccountDto.toEntity();
		userAccount.encodePassword(passwordEncoder);
		userAccountRepository.save(userAccount);
	}

	public void saveSocialUser(UserAccountDto userAccountDto) {

		if (isDuplicate(userAccountDto.getEmail())) {
			return;
		}

		userAccountRepository.save(userAccountDto.toEntity());
	}



	public void update(UserAccountDto userAccountDto) {
		UserAccount userAccount = findByEmail(userAccountDto).orElseThrow(UserNotFoundException::new);
		userAccount.update(userAccountDto);
	}

	public void delete(UserAccountDto userAccountDto) {
		UserAccount userAccount = findByEmail(userAccountDto).orElseThrow(UserNotFoundException::new);
		userAccount.setDeletedYn("Y");
		// TODO: 유저 삭제시 타 관련 정보들 전부 삭제 필요
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

