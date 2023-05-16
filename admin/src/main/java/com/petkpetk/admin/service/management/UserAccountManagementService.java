package com.petkpetk.admin.service.management;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.petkpetk.admin.config.properties.ProjectProperties;
import com.petkpetk.admin.dto.UserAccountDto;
import com.petkpetk.admin.dto.response.UserAccountApiResponse;
import com.petkpetk.admin.exception.UserAccountDeleteFailureException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserAccountManagementService {
	private final RestTemplate restTemplate;
	private final ProjectProperties projectProperties;

	public List<UserAccountDto> getUserAccounts() {
		URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.getPetkPetk().getUrl() + "/api/userAccounts")
			.queryParam("size", 10000)
			.build()
			.toUri();

		return Optional.ofNullable(restTemplate.getForObject(uri, UserAccountApiResponse.class))
			.orElseGet(UserAccountApiResponse::empty)
			.get_embedded()
			.getUserAccounts();
	}

	public UserAccountDto getUserAccount(String id) {
		URI uri = UriComponentsBuilder.fromHttpUrl(projectProperties.getPetkPetk().getUrl() + "/api/userAccounts/" + id)
			.build()
			.toUri();

		return Optional.ofNullable(restTemplate.getForObject(uri, UserAccountDto.class))
			.orElseThrow(() -> new NoSuchElementException("게시글이 없습니다 - userId: " + id));
	}

	public void deleteUserAccount(String userId) {
		String url = projectProperties.getPetkPetk().getUrl() + "/api/user/delete?id=" + userId;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Void> entity = new HttpEntity<>(headers);
		ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new UserAccountDeleteFailureException();
		}
	}
}
