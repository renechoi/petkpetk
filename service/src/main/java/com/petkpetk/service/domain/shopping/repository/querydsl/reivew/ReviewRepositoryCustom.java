package com.petkpetk.service.domain.shopping.repository.querydsl.reivew;

import java.util.List;

import com.petkpetk.service.domain.shopping.entity.review.Review;

public interface ReviewRepositoryCustom {
	List<Review> findAllByItem_IdWhereDeletes_YnIsY(Long itemId);

}
