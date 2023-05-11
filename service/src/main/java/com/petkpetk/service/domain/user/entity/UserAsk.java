package com.petkpetk.service.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.domain.user.constant.RoleType;
import com.petkpetk.service.domain.user.constant.AnswerStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "user_ask")
@Entity
@DynamicUpdate
@Where(clause = "deleted_yn='N'")
public class UserAsk extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_ask_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name ="user_account_id")
	@ToString.Exclude
	private UserAccount userAccount;

	// private AdminAskAnswer adminAskAnswer;  //Todo:: 관리자가 답변하면 여기에 그 답변 정보를 담아준다.

	@Column(nullable = false)
	private String content;
	private String phoneNumber;
	private String agree;

	private String qnaCategory;

	@Enumerated(EnumType.STRING)
	private AnswerStatus answerStatus = AnswerStatus.ANSWERING; //Todo:: 관리자가 답변하면 AnswerStatus.ANSWERED 로 바꿔줘야한다.

	public void mapUserAccount(UserAccount userAccount) {
		if (userAccount.getRoles().stream().anyMatch(roleType -> roleType.equals(RoleType.ANONYMOUS))){
			this.userAccount= null;
			return;
		}
		this.userAccount = userAccount;
	}

	@PrePersist
	public void anonymousSetup() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || "anonymousUser".equals(authentication.getName())
		) {
			this.createdBy = "anonymousUser";
			this.modifiedBy = "anonymousUser";
		}
	}
}
