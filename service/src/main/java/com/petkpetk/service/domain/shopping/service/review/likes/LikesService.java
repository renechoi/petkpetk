package com.petkpetk.service.domain.shopping.service.review.likes;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.dto.review.likes.request.LikesRequest;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.likes.Likes;
import com.petkpetk.service.domain.shopping.repository.review.ReviewRepository;
import com.petkpetk.service.domain.shopping.repository.review.likes.LikesRepository;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LikesService {

	private final LikesRepository likesRepository;
	private final UserAccountRepository userAccountRepository;
	private final ReviewRepository reviewRepository;

	public void plusLike(Long reviewId, String userEmail) {
		UserAccount userAccount = userAccountRepository.findByEmail(userEmail).get();
		Review review = reviewRepository.findById(reviewId).get();
		LikesRequest likesRequest = LikesRequest.of(userAccount, review);

		likesRepository.save(likesRequest.toEntity());

	}

	public void minusLike(Long reviewId, String userEmail) {
		UserAccount userAccount = userAccountRepository.findByEmail(userEmail).get();
		Review review = reviewRepository.findById(reviewId).get();
		Likes likes = likesRepository.findByUserAccountAndReview(userAccount, review);
		System.out.println("================================== likes = " + likes);
		likesRepository.deleteById(likes.getId());

	}

	public HashMap<String, String> findHistoryLikeByUser(Long id) {
		UserAccount userAccount = userAccountRepository.findById(id).get();
		List<Likes> likes  =likesRepository.findAllByUserAccount(userAccount);
		HashMap<String, String> hashMap = new HashMap<>();
		for (Likes like : likes) {
			hashMap.put("review" + like.getReview().getId(), "Y");
		}

		return hashMap;

	}

}

// 	public Likes findLikes()
// }
