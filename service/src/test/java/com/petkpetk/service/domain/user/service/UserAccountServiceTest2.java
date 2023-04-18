package com.petkpetk.service.domain.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.common.RoleType;
import com.petkpetk.service.config.security.oauth2.OAuth2ProviderInfo;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.embedded.Address;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.exception.UserDuplicateException;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
class UserAccountServiceTest2 {

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private UserAccountService userAccountService;

	@DisplayName("실제 db 테스트 - 이메일이 db에 있는 경우 UserAccount를 반환한다")
	@Test
	void findByEmail_returnsUserAccount_whenEmailExistsInDB() {
		// given
		String email = "user@user.com";
		UserAccountDto userAccountDto = new UserAccountDto();
		userAccountDto.setEmail(email);

		// when
		Optional<UserAccount> optionalUserAccount = userAccountService.searchUser(userAccountDto);

		// then
		assertTrue(optionalUserAccount.isPresent());
		UserAccount userAccount = optionalUserAccount.get();
		assertEquals("user@user.com", userAccount.getEmail());
	}

	@DisplayName("실제 db 테스트 - 이메일이 db에 없는 경우 빈 Optional을 반환한다")
	@Test
	void findByEmail_returnsEmpty_whenEmailDoesNotExistInDB() {
		// given
		String email = "nonexistent@user.com";
		UserAccountDto userAccountDto = new UserAccountDto();
		userAccountDto.setEmail(email);

		// when
		Optional<UserAccount> optionalUserAccount = userAccountService.searchUser(userAccountDto);

		// then
		assertFalse(optionalUserAccount.isPresent());
	}

	@DisplayName("실제 db 테스트 - 정상 회원가입을 테스트 한다")
	@Test
	void save_createsNewUserAccount() {
		// given
		UserAccountDto newUserAccountDto = new UserAccountDto(null, "newuser@user.com", "password", "새로운 유저", "newuser",
			new Address("12345", "강남", "2", "etc"), "profile.jpg", OAuth2ProviderInfo.NAVER, Set.of(RoleType.USER));

		// when
		userAccountService.save(newUserAccountDto);

		// then
		Optional<UserAccountDto> optionalUserAccountDto = userAccountService.searchUserDto("newuser@user.com");
		assertTrue(optionalUserAccountDto.isPresent());
		UserAccountDto userAccountDto = optionalUserAccountDto.get();
		assertEquals("newuser@user.com", userAccountDto.getEmail());
		assertEquals("password", userAccountDto.getPassword());
		assertEquals("새로운 유저", userAccountDto.getName());
		assertEquals("newuser", userAccountDto.getNickname());
		assertEquals("12345", userAccountDto.getAddress().getZipCode());
		assertEquals("강남", userAccountDto.getAddress().getAddress1());
		assertEquals("2", userAccountDto.getAddress().getAddress2());
		assertEquals("etc", userAccountDto.getAddress().getAddressEtc());
		assertEquals("profile.jpg", userAccountDto.getProfileImage());
		assertEquals(OAuth2ProviderInfo.NAVER, userAccountDto.getOAuth2ProviderInfo());
		assertEquals(Set.of(RoleType.USER), userAccountDto.getRoles());
	}

	@Test
	@DisplayName("실제 db 테스트 - 중복 가입 이메일로 회원가입에 실패한다")
	void saveTest_userAccountDuplicateException() {
		// given
		UserAccountDto newUserAccountDto = new UserAccountDto(null, "user@user.com", "password", "새로운 유저", "newuser",
			new Address("12345", "강남", "2", "etc"), "profile.jpg", OAuth2ProviderInfo.NAVER, Set.of(RoleType.USER));

		// when & then
		assertThrows(UserDuplicateException.class, () -> userAccountService.save(newUserAccountDto));
	}

	@DisplayName("실제 db 테스트 - 기존 사용자 계정 업데이트 한다")
	@Test
	void update_updatesExistingUserAccount() {

		// given
		String email = "user@user.com";
		UserAccountDto updatedUserAccountDto = new UserAccountDto(null, "user@user.com", "new_password", "새로운 유저",
			"newuser", new Address("54321", "강서", "3", "etc"), "new_profile.jpg", OAuth2ProviderInfo.GOOGLE,
			Set.of(RoleType.ADMIN));

		// when
		userAccountService.update(updatedUserAccountDto);

		// then
		Optional<UserAccountDto> optionalUserAccountDto = userAccountService.searchUserDto(email);
		assertTrue(optionalUserAccountDto.isPresent());
		UserAccountDto userAccountDto = optionalUserAccountDto.get();
		assertEquals("user@user.com", userAccountDto.getEmail());
		assertEquals("new_password", userAccountDto.getPassword());
		assertEquals("새로운 유저", userAccountDto.getName());
		assertEquals("newuser", userAccountDto.getNickname());
		assertEquals("54321", userAccountDto.getAddress().getZipCode());
		assertEquals("강서", userAccountDto.getAddress().getAddress1());
		assertEquals("3", userAccountDto.getAddress().getAddress2());
		assertEquals("etc", userAccountDto.getAddress().getAddressEtc());
		assertEquals("new_profile.jpg", userAccountDto.getProfileImage());
		assertEquals(Set.of(RoleType.ADMIN), userAccountDto.getRoles());
	}

}