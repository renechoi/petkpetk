package com.petkpetk.service.domain.shopping.entity.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.shopping.dto.review.response.ReviewResponse;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "review")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Review extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="item_id")
	private Item item;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	@Column(nullable = false, length = 1500)
	private String content;

	private Long likes = 0L;



	public Review(Item item, UserAccount userAccount, String content, Long likes) {
		this.item = item;
		this.userAccount = userAccount;
		this.content = content;
		this.likes = likes;

	}

	public static Review of(Item item, UserAccount userAccount, String content, Long likes) {
		return new Review(item, userAccount, content, likes);
	}

	public void updateReview(ReviewResponse reviewResponse) {
		this.item = reviewResponse.getItem();
		this.content = reviewResponse.getContent();
		this.userAccount = reviewResponse.getUserAccount();
		this.likes = reviewResponse.getLikes();
	}

}

