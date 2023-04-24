package com.petkpetk.service.domain.shopping.repository.querydsl.reivew;

import java.util.List;

import com.petkpetk.service.domain.shopping.entity.review.QReview;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom{

	private JPAQueryFactory queryFactory;
	@Override
	public List<Review> findAllByItem_IdWhereDeletes_YnIsY(Long itemId) {

		QReview review = QReview.review;
		List<Review> reviewList = queryFactory
			.selectFrom(review)
			.where(review.item.id.eq(itemId))
			.where(review.deletedYn.eq("N"))
			.orderBy(review.likes.desc())
			.orderBy(review.id.desc())
			.fetch();

		return reviewList;
	}
}
