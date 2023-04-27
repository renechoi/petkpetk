package com.petkpetk.service.domain.shopping.service.review;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.config.converter.ImageConverter;
import com.petkpetk.service.config.file.ImageLocalRepository;
import com.petkpetk.service.domain.shopping.dto.review.ReviewDto;
import com.petkpetk.service.domain.shopping.dto.review.ReviewImageDto;
import com.petkpetk.service.domain.shopping.dto.review.request.ReviewRegisterRequest;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.ReviewImage;
import com.petkpetk.service.domain.shopping.repository.review.ReviewImageRepository;
import com.petkpetk.service.domain.shopping.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ReviewImageRepository reviewImageRepository;
	private final ImageLocalRepository<ReviewImage> imageLocalRepository;

	@PersistenceContext
	private EntityManager entityManager;

	public void addReview(ReviewDto reviewDto) {
		List<ReviewImage> images = ImageConverter.of(ReviewImage::from).convertToImages(reviewDto.getRawImages());

		reviewRepository.save(reviewDto.toEntity(images));

		IntStream.range(0, images.size())
			.forEach(image -> imageLocalRepository.save(images.get(image), reviewDto.getRawImages().get(image)));
	}

	public List<ReviewResponse> getReviewList(Long itemId) {
		List<ReviewResponse> reviewResponses = new ArrayList<>();
		List<Review> reviews = reviewRepository.findAllByItem_Id(itemId);

		for (Review review : reviews) {
			ReviewResponse reviewResponse = ReviewResponse.from(review);
			reviewResponses.add(reviewResponse);
		}

		return reviewResponses;
	}

	public boolean plusLike(Long num, Long reviewId, Long likeNum) {
		Review review =  entityManager.find(Review.class, reviewId);
		review.setLikes(likeNum + 1);
		entityManager.flush();

		return true;
	}

	public boolean minusLike(Long num, Long reviewId, Long likeNum) {
		Review review =  entityManager.find(Review.class, reviewId);
		review.setLikes(review.getLikes() - 1);
		entityManager.flush();

		return true;
	}

	public void deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).get();
		review.setDeletedYn("Y");

		List<ReviewImage> reviewImages = reviewImageRepository.findByReviewIdOrderByIdAsc(reviewId);
		reviewImages.forEach(reviewImage -> reviewImage.setDeletedYn("Y"));

		imageLocalRepository.deleteFiles(reviewImages);
	}

	public ReviewResponse modifyReview(ReviewRegisterRequest reviewRegisterRequest, Long reviewId) {
		Review review = reviewRepository.findById(reviewId).get();

		List<ReviewImage> reviewImages = review.getImages();
		reviewRegisterRequest.getImages().removeIf(MultipartFile::isEmpty);

		List<ReviewImage> notModifiedImages = reviewRegisterRequest.getReviewImageDtos()
			.stream()
			.map(ReviewImageDto::getUniqueName)
			.map(reviewImageRepository::findByUniqueName)
			.collect(Collectors.toList());

		List<ReviewImage> newlyAddedImages = reviewRegisterRequest.getImages().stream()
			.map(image -> !image.isEmpty() ? ReviewImage.from(image) : null)
			.collect(Collectors.toList());

		List<ReviewImage> allRequestedImages = Stream.concat(notModifiedImages.stream(), newlyAddedImages.stream())
			.collect(Collectors.toList());

		List<ReviewImage> imagesToDelete = reviewImages.stream()
			.filter(reviewImage -> !allRequestedImages.contains(reviewImage))
			.collect(Collectors.toList());

		imageLocalRepository.deleteFiles(imagesToDelete);

		IntStream.range(0, newlyAddedImages.size())
			.forEach(image -> imageLocalRepository.save(newlyAddedImages.get(image),
				reviewRegisterRequest.getImages().get(image)));

		reviewImages.removeIf(reviewImage -> !notModifiedImages.contains(reviewImage));

		reviewImages.addAll(newlyAddedImages);
		review.mapImages(reviewImages);
		review.setContent(reviewRegisterRequest.getContent());

		return ReviewResponse.from(review);
	}


	public void noImageModity(ReviewDto reviewDto, Long reviewId, List<MultipartFile> rawImages) {
		Review review = reviewRepository.findById(reviewId).get();

		if (rawImages == null) {

		} else {
			List<ReviewImage> images = ImageConverter.of(ReviewImage::from).convertToImages(rawImages);

			IntStream.range(0, images.size())
				.filter(i -> !rawImages.get(i).isEmpty())
				.forEach(i -> imageLocalRepository.save(images.get(i), rawImages.get(i)));

			review.addImage(images);
		}

		review.setContent(reviewDto.getContent());
	}
}
