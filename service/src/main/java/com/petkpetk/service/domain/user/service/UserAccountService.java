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
		UserAccount userAccount = findByEmail(userAccountPrincipal.getEmail()).orElseThrow(UserNotFoundException::new);

		return userAccountPrincipal.getProfileImage() != null ?
			UserUpdateRequest.from(userAccount, userAccountPrincipal instanceof OAuth2UserAccountPrincipal ? null :
				imageLocalRepository.findByPetkpetkImage(userAccount.getProfileImage())) :
			UserUpdateRequest.from(userAccount);
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
		UserAccount userAccount = findByEmail(userUpdateRequest.getEmail()).orElseThrow(UserNotFoundException::new);
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
		UserAccount userAccount = findByEmail(userAccountDto.getEmail()).orElseThrow(
			UserNotFoundException::new);
		userAccount.setDeletedYn("Y");
		// TODO: 유저 삭제시 타 관련 정보들 전부 삭제 필요
	}

	public UserAccountDto searchUserDto(String email) {
		return userAccountRepository.findByEmail(email).map(UserAccountDto::fromEntity).orElseThrow(UserNotFoundException::new);
	}

	public ProfileImage getUserProfile(UserAccountPrincipal userAccountPrincipal) {
		return profileImageRepository.findByUserAccountId(userAccountPrincipal.getId())
			.orElseThrow(UserNotFoundException::new);
	}

	public boolean isPasswordSame(String password, String email) {
		return findByEmail(email).orElseThrow(UserNotFoundException::new).checkPassword(password, passwordEncoder);
	}

	public boolean isEmailDuplicate(String email) {
		return findByEmail(email).isPresent();
	}

	public boolean isNicknameDuplicate(String nickName, String email) {
		// 초기 회원가입의 경우 => 즉 이메일이 없는 경우 단순 nickname 중복값만 체크
		// 수정의 경우 => 이메일을 받아서 1) 본인것과 같은지 => 허용 / 2) 중복 값이 있는지
		return email == null ? isNickNamePresent(nickName) :
			!userAccountRepository.findByNickname(nickName)
				.orElseThrow(UserNotFoundException::new)
				.getNickname()
				.equals(nickName) && isNickNamePresent(nickName);
	}

	private boolean isNickNamePresent(String nickName) {
		return userAccountRepository.findByNickname(nickName).isPresent();
	}

	private boolean isDuplicate(String email) {
		return userAccountRepository.findByEmail(email).isPresent();
	}

	private Optional<UserAccount> findByEmail(String email) {
		return userAccountRepository.findByEmail(email);
	}
}
