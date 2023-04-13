package com.petkpetk.service.domain.board.dto;

import java.time.LocalDate;

import com.petkpetk.service.common.converter.EntityAndDtoConverter;
import com.petkpetk.service.domain.board.entity.Comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	private Long commentId;
	private Long articleId;
	private Long userId;
	private String content;
	private Long parentCommentId;
	private LocalDate createdAt;
	private LocalDate modifiedAt;
	private LocalDate deletedAt;
	private String deleteYn;

	public Comment toEntity() {
		return EntityAndDtoConverter.convertToEntity(this, Comment.class);
	}

	public static CommentDto fromEntity(Comment comment) {
		return EntityAndDtoConverter.convertToDto(comment, CommentDto.class);
	}

	public static CommentDto of(Long commentId, Long articleId, Long userId, String content,
		Long parentCommentId,
		LocalDate createdAt, LocalDate modifiedAt, LocalDate deletedAt, String deleteYn) {
		return new CommentDto(
		commentId,
		articleId,
		userId,
		content,
		parentCommentId,
		createdAt,
		modifiedAt,
		deletedAt,
		deleteYn
		);
	}
}
