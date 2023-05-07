package com.petkpetk.service.domain.community.dto;

import java.util.LinkedHashSet;
import java.util.Set;

import com.petkpetk.service.domain.community.entity.ArticleComment;
import com.petkpetk.service.domain.user.dto.UserAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentDto {
	// todo : 구현

	private Long id;
	private ArticleDto articleDto;
	private UserAccountDto userAccountDto;
	private String content;
	private Long parentCommentId;
	private Set<ArticleCommentDto> childComments = new LinkedHashSet<>();

	public ArticleComment toEntity() {
		return new ArticleComment(
			this.getArticleDto().toEntity(),
			this.getUserAccountDto().toEntity(),
			this.getContent(),
			this.getParentCommentId()
		);
	}

}
