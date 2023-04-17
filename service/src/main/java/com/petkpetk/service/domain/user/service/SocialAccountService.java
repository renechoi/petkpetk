package com.petkpetk.service.domain.user.service;

import static com.petkpetk.service.common.StatusCode.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.config.exception.PetkpetkServerException;
import com.petkpetk.service.config.security.oauth2.OAuth2UserInfo;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

/**
 * OAuth@UserService 클래스명을 spring security 이미 사용하고 있어서
 * SocialUserService 명명
 */

@Service
@Transactional
public class SocialAccountService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserAccountRepository userAccountRepository;

	private final Map<String, OAuth2UserInfo> userInfoMap = new HashMap<>();

	@Autowired
	public SocialAccountService(UserAccountRepository userAccountRepository) {
		userInfoMap.put(OAuth2ProviderInfo.GOOGLE.getProviderId(), GOOGLE);
		userInfoMap.put(OAuth2ProviderInfo.KAKAO.getProviderId(), KAKAO);
		userInfoMap.put(OAuth2ProviderInfo.NAVER.getProviderId(), NAVER);
		this.userAccountRepository = userAccountRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		String clientRegistrationId = userRequest.getClientRegistration().getRegistrationId();
		OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();

		OAuth2User oAuth2User = userService.loadUser(userRequest);

		OAuth2UserInfo userInfo = userInfoMap.get(clientRegistrationId.toLowerCase());
		if (userInfo != null) {
			return userInfo.register(oAuth2User);
		}

		throw new PetkpetkServerException(OAUTH2_PROVIDER_ERROR);
	}

	public void save(String name, String email) {
		boolean exists = userAccountRepository.existsByEmail(email);

		System.out.println("영속화 !!! name = " + name);

		if (!exists) {
			// todo : db 영속화
		}
	}

	public OAuth2UserInfo GOOGLE = (oAuth2User) -> {
		String name = oAuth2User.getAttributes().get("name").toString();
		String email = oAuth2User.getAttributes().get("email").toString();
		save(name, email);

		return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "sub");
	};

	public OAuth2UserInfo KAKAO = (oAuth2User) -> {
		Map<String, Object> properties = oAuth2User.getAttribute("properties");
		Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");

		String name = properties.get("nickname").toString();
		String email = kakaoAccount.get("email").toString();
		save(name, email);

		return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "id");
	};

	public OAuth2UserInfo NAVER = (oAuth2User) -> {
		Map<String, Object> attributes = oAuth2User.getAttribute("response");

		String name = attributes.get("name").toString();
		String email = attributes.get("email").toString();
		String nickname = attributes.get("nickname").toString();
		String profileImage = attributes.get("profile_image").toString();
		save(name, email);

		return new DefaultOAuth2User(oAuth2User.getAuthorities(), oAuth2User.getAttributes(), "response");
	};
}






