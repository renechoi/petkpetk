package com.petkpetk.service.domain.shopping.repository.querydsl.reivew;

import java.util.List;

import javax.persistence.EntityManager;

import com.petkpetk.service.domain.shopping.dto.review.ManageReviewDto;
import com.petkpetk.service.domain.shopping.entity.item.QItem;
import com.petkpetk.service.domain.shopping.entity.item.QItemImage;
import com.petkpetk.service.domain.shopping.entity.review.QReview;
import com.petkpetk.service.domain.shopping.entity.review.QReviewImage;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

	private JPAQueryFactory queryFactory;

	public ReviewRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

	@Override
	public List<ManageReviewDto> getUserReviewList(String email) {
		QItem item = QItem.item;
		QItemImage itemImage = QItemImage.itemImage;
		QReview review = QReview.review;
		QReviewImage reviewImage = QReviewImage.reviewImage;

		List<ManageReviewDto> reviewList = queryFactory
			.select(
				Projections.constructor(
					ManageReviewDto.class,
					review.id,
					review.item.itemName,
					review.item.itemStatus,
					itemImage.imageUrl,
					review.item.originalPrice,
					review.item.discountRate,
					review.item.price,
					review.item.totalRating
				)
			)
			.from(review)
			.leftJoin(itemImage).on(itemImage.item.eq(review.item))
			.where(itemImage.representativeImageYn.eq("Y"))
			.where(review.deletedYn.eq("N"))
			.where(review.userAccount.email.eq(email))
			.orderBy(review.id.desc())
			.fetch();

		System.out.println("reviewList = " + reviewList);
		return reviewList;
	}
}
