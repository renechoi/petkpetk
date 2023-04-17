package com.petkpetk.service.domain.board.dto;

import java.time.LocalDate;

import com.petkpetk.service.config.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.board.entity.Article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

	private Long articleId;
	private Long userId;

	private String title;
	private String content;
	private Long hit;
	private Long like;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private Boolean deleteYn;
	private String img;

	public Article toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Article.class);
	}

	public static ArticleDto fromEntity(Article article) {
		return EntityAndDtoConverter.convertToDto(article, ArticleDto.class);
	}

	public static ArticleDto of(Long articleId, Long userId, String title, String content, Long hit, Long like, LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, Boolean deleteYn, String img) {
		return new ArticleDto(
		articleId,
		userId,
		title,
		content,
		hit,
		like,
		createdAt,
		modifiedAt,
		deletedAt,
		deleteYn,
		img
		);
	}

	public static ArticleDto toArticleDto(Article article) {
		ArticleDto articleDto = new ArticleDto();
		articleDto.setArticleId(article.getArticleId());
		articleDto.setUserId(article.getUserId());
		articleDto.setTitle(article.getTitle());
		articleDto.setContent(article.getContent());
		articleDto.setHit(article.getHit());
		articleDto.setLike(article.getLike());
		articleDto.setCreatedAt(article.getCreatedAt());
		articleDto.setModifiedAt(article.getModifiedAt());
		articleDto.setDeletedAt(article.getDeletedAt());
		articleDto.setDeleteYn(article.getDeleteYn());
		return articleDto;
	}

}
