package com.petkpetk.service.domain.shopping.repository.querydsl.reivew;

import java.util.List;

import com.petkpetk.service.domain.shopping.dto.review.ManageReviewDto;

public interface ReviewRepositoryCustom {
	List<ManageReviewDto> getUserReviewList(String email);
}
