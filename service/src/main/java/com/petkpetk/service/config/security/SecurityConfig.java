package com.petkpetk.service.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.service.UserAccountService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(
				auth -> auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll()
					.mvcMatchers("/", "/user/**", "/error/**", "/login", "seller/sign-up")
					.permitAll()
					.anyRequest()
					.authenticated())

			.formLogin(formLogin -> formLogin.loginPage("/login")
				.loginProcessingUrl("/login/process")
				.defaultSuccessUrl("/")
				.failureUrl("/login")
				.usernameParameter("email")
				.passwordParameter("password"))

			.logout(
				logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/"))

			.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()

			.build();
	}

	@Bean
	public UserDetailsService userDetailsService(UserAccountService userAccountService) {
		return email -> userAccountService.searchUserDto(email)
			.map(UserAccountPrincipal::from)
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다"));
	}

}
