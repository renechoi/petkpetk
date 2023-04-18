package com.petkpetk.service.domain.user.service;

import java.util.Set;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.embedded.Address;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OidcUserAccountService implements OAuth2UserService<OidcUserRequest, OidcUser> {

	private final UserAccountService userAccountService;

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

		OidcUserService oidcUserService = new OidcUserService();
		OidcUser oidcUser = oidcUserService.loadUser(userRequest);

		UserAccountDto userAccountDto = UserAccountDto.of(
			null,
			oidcUser.getAttributes().get("email").toString(),
			"undefined",
			oidcUser.getAttributes().get("name").toString(),
			"undefined",
			new Address(),
			oidcUser.getAttributes().get("picture").toString(),
			OAuth2ProviderInfo.GOOGLE,
			Set.of(RoleType.USER)
		);

		userAccountService.saveSocialUser(userAccountDto);

		return new DefaultOidcUser(
			oidcUser.getAuthorities(), oidcUser.getIdToken(), oidcUser.getUserInfo()
		);
	}
}
