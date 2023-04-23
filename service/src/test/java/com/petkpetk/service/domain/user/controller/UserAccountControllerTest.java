package com.petkpetk.service.domain.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.petkpetk.service.config.security.oauth2.provider.GoogleOAuth2Provider;
import com.petkpetk.service.config.security.oauth2.provider.KakaoOAuth2Provider;
import com.petkpetk.service.config.security.oauth2.provider.NaverOAuth2Provider;
import com.petkpetk.service.domain.testconfig.TestSecurityConfig;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;
import com.petkpetk.service.domain.user.service.OAuth2UserAccountService;
import com.petkpetk.service.domain.user.service.UserAccountService;

@ActiveProfiles("test")
@Import({TestSecurityConfig.class, GoogleOAuth2Provider.class, KakaoOAuth2Provider.class, NaverOAuth2Provider.class, OAuth2UserAccountService.class})
@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	// @MockBean
	@Autowired
	private UserAccountService userAccountService;

	@MockBean
	private UserAccountRepository userAccountRepository;

	@DisplayName("로그인 페이지 - 정상 호출")
	@WithAnonymousUser
	@Test
	public void givenNothing_whenTryingToLogIn_thenReturnsLogInView() throws Exception {
		// Given

		// When & Then
		mockMvc.perform(get("/user/login"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		then(userAccountService).shouldHaveNoInteractions();
	}

	@DisplayName("회원가입 페이지 - 정상 호출")
	@WithAnonymousUser
	@Test
	public void givenNothing_whenTryingToAccessSignUpPage_thenReturnsSignUpPage() throws Exception {
		mockMvc.perform(get("/user/sign-up"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("user/sign-up"))
			.andExpect(model().attributeExists("userAccount"));
		then(userAccountService).shouldHaveNoInteractions();
	}

	// @DisplayName("내 정보 수정 페이지 - 로그인한 사용자")
	// @Test
	// public void givenAuthenticatedUser_whenAccessingUpdatePage_thenReturnsMyPageWithUserData() throws Exception {
	// 	// given
	// 	UserAccountDto userAccountDto = UserAccountDto.of(1L, "user@user.com", "password", "홍길동", "길동",
	// 		Address.of("34589", "서울특별시 광진구", "자바동", "기타"), "profileUrl", SignUpProvider.NAVER, Set.of(RoleType.USER));
	// 	given(userAccountService.searchUserDto(any(UserAccountDto.class))).willReturn(Optional.of(userAccountDto));
	//
	// 	// when & then
	// 	mockMvc.perform(get("/user/my-page/update").with(user(UserAccountPrincipal.from(userAccountDto))))
	// 		.andExpect(status().isOk());
	// 		// .andExpect(view().name("user/my-page"))
	// 		// .andExpect(model().attribute("userAccount", UserAccountResponse.from(userAccountDto)));
	//
	// 	then(userAccountService).should().searchUserDto(userAccountDto.getEmail());
	// }

	//
	// // @WithMockUser("anonymousUser")
	// @DisplayName("회원가입 - 유효한 데이터")
	// @Test
	// public void givenValidSignUpRequest_whenSignUp_thenRedirectToLoginPage() throws Exception {
	// 	// Given
	// 	UserAccountRequest request = new UserAccountRequest();
	// 	request.setId(1L);
	// 	request.setEmail("test@test.com");
	// 	request.setPassword("password1234");
	// 	request.setName("홍길동");
	// 	request.setNickname("honggd");
	// 	request.setAddress(new Address("12345", "서울시 강남구 역삼동", "2", "etc"));
	//
	// 	// When & Then
	// 	mockMvc.perform(post("/user/sign-up")
	// 			.param("email", request.getEmail())
	// 			.param("password", request.getPassword())
	// 			.param("name", request.getName())
	// 			.param("nickname", request.getNickname())
	// 			.param("address.zipCode", request.getAddress().getZipCode())
	// 			.param("address.address1", request.getAddress().getAddress1())
	// 			.param("address.address2", request.getAddress().getAddress2())
	// 			.param("address.addressEtc", request.getAddress().getAddressEtc()))
	// 		.andExpect(status().is3xxRedirection())
	// 		.andExpect(redirectedUrl("/user/login"));
	//
	// 	then(userAccountService).should().save(request.toDto());
	// }

}
