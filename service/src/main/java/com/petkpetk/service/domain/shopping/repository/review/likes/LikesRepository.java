package com.petkpetk.service.domain.shopping.repository.review.likes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.likes.Likes;
import com.petkpetk.service.domain.user.entity.UserAccount;

public interface LikesRepository extends JpaRepository<Likes, Long> {
	Likes findByUserAccountAndReview(UserAccount userAccount, Review review);

	List<Likes> findAllByUserAccount(UserAccount userAccount);
}
