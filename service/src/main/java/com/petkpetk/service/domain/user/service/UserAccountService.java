package com.petkpetk.service.domain.user.service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.config.converter.ImageConverter;
import com.petkpetk.service.config.exception.PetkpetkServerException;
import com.petkpetk.service.config.file.ImageLocalRepository;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.dto.request.UserSignupRequest;
import com.petkpetk.service.domain.user.dto.request.UserUpdateRequest;
import com.petkpetk.service.domain.user.dto.security.OAuth2UserAccountPrincipal;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;
import com.petkpetk.service.domain.user.entity.ProfileImage;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.exception.UserDuplicateException;
import com.petkpetk.service.domain.user.exception.UserNotFoundException;
import com.petkpetk.service.domain.user.repository.ProfileImageRepository;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserAccountService {

	private final UserAccountRepository userAccountRepository;
	private final ImageLocalRepository<ProfileImage> imageLocalRepository;
	private final ProfileImageRepository profileImageRepository;
	private final PasswordEncoder passwordEncoder;

	public void save(UserSignupRequest userSignupRequest) {
		if (isDuplicate(userSignupRequest.getEmail())) {
			throw new UserDuplicateException();
		}

		ProfileImage profileImage = ImageConverter.of(ProfileImage::from)
			.convertToImage(userSignupRequest.getProfileImage());

		UserAccount userAccount = userSignupRequest.toEntity(profileImage);
		userAccount.encodePassword(passwordEncoder);
		userAccountRepository.save(userAccount);

		imageLocalRepository.save(profileImage, userSignupRequest.getProfileImage());
	}

	public void saveSocialUser(OAuth2UserAccountPrincipal oAuth2UserAccountPrincipal) {
		if (isDuplicate(oAuth2UserAccountPrincipal.getEmail())) {
			return;
		}

		userAccountRepository.save(oAuth2UserAccountPrincipal.toEntity());
	}

	public UserUpdateRequest getUserUpdateRequestView(UserAccountPrincipal userAccountPrincipal) {
		UserAccount userAccount = searchUser(userAccountPrincipal);

		if (userAccountPrincipal.getProfileImage() != null) {
			MultipartFile profileRawImage =
				userAccountPrincipal instanceof OAuth2UserAccountPrincipal ? null :
					imageLocalRepository.findByPetkpetkImage(userAccount.getProfileImage());
			return UserUpdateRequest.from(userAccount, profileRawImage);
		} else {
			return UserUpdateRequest.from(userAccount);
		}

	}

	/**
	 * 이미지가 바뀌는 경우와 바뀌지 않는 경우를 나누어서 생각한다.
	 * 1) 바뀌지 않은 경우 : PreviousImage 와 profileImage 가 같다면 로컬에서 해줄 일이 없음
	 * 2) 바뀐 경우 :  previousImaged와 profileImage가 다르다면 로컬에서 해줄 일
	 * - 1. 기존 것 삭제
	 * - 2. profileImage 저장
	 * 이미지 비교는 이미 객체 내부에서 Equals and hash 코드를 재정의 했으므로 그냥 비교하면 된다.
	 */
	public void update(UserUpdateRequest userUpdateRequest) {
		UserAccount userAccount = findByEmail(userUpdateRequest);
		ProfileImage previousImage = userAccount.getProfileImage();

		if (userUpdateRequest.getProfileImage().isEmpty()) {
			userAccount.update(userUpdateRequest);
			return;
		}

		ProfileImage profileImage = ImageConverter.of(ProfileImage::from)
			.convertToImage(userUpdateRequest.getProfileImage());
		userAccount.update(userUpdateRequest, profileImage);

		Optional.ofNullable(previousImage).ifPresent(profileImageRepository::delete);

		Optional.ofNullable(previousImage)
			.filter(image -> !image.equals(profileImage))
			.ifPresent(image -> {
				imageLocalRepository.delete(previousImage);
				imageLocalRepository.save(profileImage, userUpdateRequest.getProfileImage());
			});

	}

	public void delete(UserAccountDto userAccountDto) {
		UserAccount userAccount = findByEmail(userAccountDto).orElseThrow(
			UserNotFoundException::new);
		userAccount.setDeletedYn("Y");
		// TODO: 유저 삭제시 타 관련 정보들 전부 삭제 필요
	}

	public Optional<UserAccountDto> searchUserDto(String email) {
		return userAccountRepository.findByEmail(email).map(UserAccountDto::fromEntity);
	}

	public Optional<UserAccount> searchUser(String email) {
		return userAccountRepository.findByEmail(email);
	}

	public UserAccount getCurrentPrincipal(Authentication authentication) {
		return searchUser(authentication.getName())
			.orElseThrow(PetkpetkServerException::new);
	}

	private boolean isDuplicate(String email) {
		return userAccountRepository.findByEmail(email).isPresent();
	}

	private Optional<UserAccount> findByEmail(UserAccountDto userAccountDto) {
		return userAccountRepository.findByEmail(userAccountDto.getEmail());
	}

	private UserAccount findByEmail(UserUpdateRequest userUpdateRequest) {
		return userAccountRepository.findByEmail(userUpdateRequest.getEmail())
			.orElseThrow(UserNotFoundException::new);
	}

	public UserAccount searchUser(UserAccountPrincipal userAccountPrincipal) {
		return userAccountRepository.findByEmail(userAccountPrincipal.getEmail())
			.orElseThrow(UserNotFoundException::new);
	}

	public ProfileImage getUserProfile(UserAccountPrincipal userAccountPrincipal) {
		ProfileImage profileImage = profileImageRepository.findById(userAccountPrincipal.getId()).get();

		return profileImage;
	}

	public Optional<UserAccount> searchUserByNickName(String nickName) {
		return userAccountRepository.findByNickname(nickName);
	}
}




