package com.petkpetk.service.domain.board.dto;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.board.entity.ArticleHashTag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleHashTagDto {

	private Long articleId;
	private Long hashTagId;
	private Long userId;

	public ArticleHashTag toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, ArticleHashTag.class);
	}

	public static ArticleHashTagDto fromEntity(ArticleHashTag articleHashTag) {
		return  EntityAndDtoConverter.convertToDto(articleHashTag, ArticleHashTagDto.class);
	}

	public static ArticleHashTagDto of(Long articleId, Long hashTagId, Long userId) {
		return new ArticleHashTagDto(
		articleId,
		hashTagId,
		userId
		);
	}
}
