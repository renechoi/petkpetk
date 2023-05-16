package com.petkpetk.admin.config.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.petkpetk.admin.dto.security.AdminAccountPrincipal;
import com.petkpetk.admin.exception.AdminAccountNotFoundException;
import com.petkpetk.admin.service.admin.AdminAccountService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final AdminAccountService adminAccountService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = authentication.getCredentials().toString();

		AdminAccountPrincipal adminAccount = adminAccountService.searchAdminDto(email)
			.map(AdminAccountPrincipal::from)
			.orElseThrow(
				AdminAccountNotFoundException::new);

		if (adminAccount == null) {
			throw new UsernameNotFoundException("Invalid username or password");
		}

		if (!passwordEncoder.matches(password, adminAccount.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}

		if (!adminAccount.isEnabled()) {
			throw new DisabledException("User is not approved yet");
		}

		return new UsernamePasswordAuthenticationToken(adminAccount, password, adminAccount.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
