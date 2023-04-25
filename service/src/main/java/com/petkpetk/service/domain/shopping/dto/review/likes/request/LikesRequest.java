package com.petkpetk.service.domain.shopping.dto.review.likes.request;

import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.entity.review.likes.Likes;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data()
@NoArgsConstructor
@AllArgsConstructor
public class LikesRequest {

	private Long id;
	private UserAccount userAccount;
	private Review review;

	public LikesRequest(UserAccount userAccount, Review review) {
		this.id = id;
		this.userAccount = userAccount;
		this.review = review;
	}

	public Likes toEntity(){
		return Likes.of(
			this.userAccount,
			this.review
		);
	}

	public static LikesRequest of(UserAccount userAccount, Review review) {
		return new LikesRequest(userAccount, review);
	}

}
