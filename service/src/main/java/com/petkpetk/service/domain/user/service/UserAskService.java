package com.petkpetk.service.domain.user.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.user.dto.UserAskDto;
import com.petkpetk.service.domain.user.dto.request.UserAskRequest;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.repository.UserAskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAskService {

	private final UserAccountService userAccountService;
	private final UserAskRepository userAskRepository;


	public void saveAsk(UserAskRequest userAskRequest, UserAccountPrincipal userAccountPrincipal) {
		if (userAccountPrincipal != null) {
			userAskRequest.setUserAccount(userAccountService.findByEmail(userAccountPrincipal.getEmail()).orElseThrow(UnknownError::new));
		}
		userAskRepository.save(userAskRequest.toEntity());
	}

	public List<UserAskDto> getUserAskList(UserAccountPrincipal userAccountPrincipal) {
		if (userAccountPrincipal != null) {
			return userAskRepository.findAllByUserAccountId(userAccountPrincipal.getId());
		}

		return null;
	}
}
