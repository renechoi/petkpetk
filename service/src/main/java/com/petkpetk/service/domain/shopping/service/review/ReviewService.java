package com.petkpetk.service.domain.shopping.service.review;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.dto.review.ReviewDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRequest;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public ReviewDto addReview(ReviewRequest reviewRequest) {
		ReviewResponse reviewResponse = ReviewResponse.of(reviewRequest);
		reviewRepository.save(reviewResponse.toEntity());

		return ReviewDto.of(reviewResponse);
	}

	public List<ReviewResponse> getReviewList(Long itemId) {
		List<ReviewResponse> reviewResponses = new ArrayList<>();
		List<Review> reviews = reviewRepository.findAllByItem_Id(itemId);

		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("-------------------------------- reviews = " + reviews);
		System.out.println("-----------------------------------------------------------------------------------------------------");

		for (Review review : reviews) {
			ReviewResponse reviewResponse = ReviewResponse.of(review);
			reviewResponses.add(reviewResponse);
		}

		return reviewResponses;
	}

	public boolean plusLike(Long num, Long reviewId, Long likeNum) {
		Review review =  entityManager.find(Review.class, reviewId);

		review.setLikes(likeNum + 1);
		ReviewResponse reviewResponse = ReviewResponse.of(review);
		review.updateReview(reviewResponse);
		entityManager.flush();

		Review reviewEntity = reviewRepository.findById(reviewId).get();
		System.out.println("♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡ reviewEntity.getLikes() = " + reviewEntity.getLikes());

		return true;
	}

	public boolean minusLike(Long num, Long reviewId, Long likeNum) {
		Review review =  entityManager.find(Review.class, reviewId);
		review.setLikes(review.getLikes() - 1);
		ReviewResponse reviewResponse = ReviewResponse.of(review);
		review.updateReview(reviewResponse);
		entityManager.flush();

		Review reviewEntity = reviewRepository.findById(reviewId).get();
		System.out.println("♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥ reviewEntity.getLikes() = " + reviewEntity.getLikes());

		return true;
	}

	public void deleteReview(Long reviewId) {
		Review review =  entityManager.find(Review.class, reviewId);
		review.setDeletedYn("Y");
		ReviewResponse reviewResponse = ReviewResponse.of(review);
		review.updateReview(reviewResponse);
		entityManager.flush();

		Review reviewEntity = reviewRepository.findById(reviewId).get();
		System.out.println("★★★★★★★★★★★★★★ reviewEntity = " + reviewEntity);
	}

	public void modifyReview(Long reviewId, String content) {
		Review review =  entityManager.find(Review.class, reviewId);
		review.setContent(content);
		entityManager.flush();

		Review reviewEntity = reviewRepository.findById(reviewId).get();
		System.out.println("★★★★★★★★★★★★★★ reviewEntity = " + reviewEntity);

	}
}
