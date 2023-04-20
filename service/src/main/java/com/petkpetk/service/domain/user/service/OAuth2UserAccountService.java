package com.petkpetk.service.domain.user.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.config.security.oauth2.OAuth2UserAccount;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.embedded.Address;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OAuth2UserAccountService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private UserAccountService userAccountService;

	private Map<String, OAuth2UserAccount> oAuth2Users = new HashMap<>();

	@Autowired
	public OAuth2UserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
		oAuth2Users = Map.of(
			OAuth2ProviderInfo.KAKAO.getProviderId(), KAKAO,
			OAuth2ProviderInfo.NAVER.getProviderId(), NAVER
		);
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();

		OAuth2User oAuth2User = userService.loadUser(userRequest);

		OAuth2UserAccount oAuth2UserAccount = oAuth2Users.get(clientRegistrationId.toLowerCase());
		return oAuth2UserAccount.signup(oAuth2User);
	}

	public OAuth2UserAccount KAKAO = (oAuth2User) -> {
		Map<String, Object> properties = oAuth2User.getAttribute("properties");
		Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");

		UserAccountDto userAccountDto = UserAccountDto.of(
			null,
			kakaoAccount.get("email").toString(),
			"undefined",
			properties.get("nickname").toString(),
			properties.get("nickname").toString(),
			new Address(),
			properties.get("profile_image").toString(),
			OAuth2ProviderInfo.KAKAO,
			Set.of(RoleType.USER),
			null,
			null,
			null
		);

		userAccountService.saveSocialUser(userAccountDto);

		return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "id");
	};

	public OAuth2UserAccount NAVER = (oAuth2User) -> {
		Map<String, Object> attributes = oAuth2User.getAttribute("response");

		UserAccountDto userAccountDto = UserAccountDto.of(
			null,
			attributes.get("email").toString(),
			"undefined",
			attributes.get("name").toString(),
			attributes.get("nickname").toString(),
			new Address(),
			attributes.get("profile_image").toString(),
			OAuth2ProviderInfo.NAVER,
			Set.of(RoleType.USER),
			null,
			null,
			null
		);

		userAccountService.saveSocialUser(userAccountDto);

		return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "response");
	};
}




