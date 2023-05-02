package com.petkpetk.service.domain.community.dto;

import java.util.LinkedHashSet;
import java.util.Set;

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
}
