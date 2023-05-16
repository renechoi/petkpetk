package com.petkpetk.admin.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.petkpetk.admin.dto.security.AdminAccountPrincipal;
import com.petkpetk.admin.service.admin.AdminAccountService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		return http.authorizeHttpRequests(
				auth -> auth.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
					.permitAll()
					.mvcMatchers("/", "/user/**", "/error/**", "/login", "/seller/sign-up", "/seller/**", "/admin/**",
						"/test/**", "item/**", "/**")
					.permitAll()
					.mvcMatchers("/api/**", "/explorer")
					.permitAll()

					.anyRequest()
					.authenticated())

			.csrf(csrf -> csrf.ignoringAntMatchers("/api/**", "management/**"))

			.formLogin(formLogin -> formLogin.loginPage("/login")
				.loginProcessingUrl("/login/process")
				.defaultSuccessUrl("/")
				.failureHandler((request, response, exception) -> {
					if (BadCredentialsException.class.isAssignableFrom(exception.getClass())) {
						request.getRequestDispatcher("/login/failure?cause=bad-credential").forward(request, response);
						return;
					}
					request.getRequestDispatcher("/login/failure").forward(request, response);
				})

				// .failureUrl("/login/error")
				.usernameParameter("email")
				.passwordParameter("password")
				.permitAll())

			.logout(
				logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/"))

			.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()

			.build();
	}

	@Bean
	public UserDetailsService userDetailsService(AdminAccountService adminAccountService) {
		return email -> adminAccountService.searchAdminDto(email)
			.map(AdminAccountPrincipal::from)
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다"));
	}
}
