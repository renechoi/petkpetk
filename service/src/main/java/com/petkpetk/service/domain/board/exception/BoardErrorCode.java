package com.petkpetk.service.domain.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BoardErrorCode {

	DUPLICATE_ARTICLE("이미 작성된 게시글이 있습니다."),

	COMMENT_NOT_FOUND("댓글 정보를 찾을 수 없습니다.")

	;

	private final String message;
}
