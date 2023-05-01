package com.petkpetk.service.domain.community.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.common.CategoryType;
import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.community.dto.ArticleDto;
import com.petkpetk.service.domain.community.dto.HashtagDto;

import lombok.Data;

@Data
public class ArticleResponse {
	// todo : 좋아요를 추가한다
	private Long id;
	private String name;
	private String email;
	private String title;
	private String content;
	private Long hit;
	private CategoryType categoryType;
	private List<MultipartFile> rawImages = new ArrayList<>();
	private Set<String> hashtags;
	private LocalDateTime createdAt;

	public static ArticleResponse from(ArticleDto articleDto) {
		ArticleResponse articleResponse = EntityAndDtoConverter.convertToDto(articleDto, ArticleResponse.class);
		articleResponse.setName(extractName(articleDto));
		articleResponse.setEmail(articleDto.getUserAccountDto().getEmail());
		articleResponse.setHashtags(articleDto.getHashtagDtos().stream()
			.map(HashtagDto::getHashTagName)
			.collect(Collectors.toSet())
		);
		System.out.println("articleResponse = " + articleResponse.getHashtags());
		articleResponse.setRawImages(articleDto.getRawImages());
		return articleResponse;
	}

	private static String extractName(ArticleDto articleDto) {
		return articleDto.getUserAccountDto().getNickname() != null ? articleDto.getUserAccountDto().getNickname() :
			articleDto.getUserAccountDto().getName();
	}
}
