package com.petkpetk.service.domain.community.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.petkpetk.service.common.AuditingFields;
import com.petkpetk.service.common.CategoryType;
import com.petkpetk.service.domain.user.entity.UserAccount;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "title"), @Index(columnList = "createdAt"), @Index(columnList = "createdBy")})
@Where(clause = "deleted_yn='N'")
@Entity
public class Article extends AuditingFields {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "article_id", length = 10)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_account_id")
	private UserAccount userAccount;

	@Column(length = 30)
	private String title;
	@Column(length = 5000)
	private String content;
	@Column(length = 10)
	private Long hit;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "article_id")
	@ToString.Exclude
	private Set<ArticleLikes> likes;

	@ToString.Exclude
	@JoinTable(name = "article_hashtag", joinColumns = @JoinColumn(name = "article_id"), inverseJoinColumns = @JoinColumn(name = "hashtag_id"))
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Hashtag> hashtags = new LinkedHashSet<>();

	@ToString.Exclude
	@OrderBy("createdAt DESC")
	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
	private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

	@OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
	@ToString.Exclude
	private List<ArticleImage> articleImages;

	@Enumerated(EnumType.STRING)
	private CategoryType categoryType;

	public void addImages(List<ArticleImage> images) {
		setRepresentativeImage(images).forEach(image -> image.mapWith(this));
		this.articleImages = images;
	}

	private List<ArticleImage> setRepresentativeImage(List<ArticleImage> images) {
		images.get(0).setRepresentativeImageYn("Y");
		return images;
	}

	/**
	 * rawHashtags 의 예시는 다음과 같다.
	 * "#123 #12345 #hashtag #example #해시태그는 #이렇게 #구성되어 #있다"
	 * <p>
	 * 해시태그를 추출하기 위한 정규식 (?<=\\s|^)#[\\p{L}0-9_]+는 다음과 같은 구성으로 이루어져 있다.
	 * <p>
	 * (?<=\\s|^) : 전방탐색(lookbehind)을 사용하여 공백 또는 문자열의 시작 부분(^)이 해시태그(#) 앞에 있는 경우에만 해시태그를 추출한다.
	 * # : 해시태그 기호(#)를 나타낸다.
	 * [\\p{L}0-9_]+ : 해시태그 이름으로 사용될 수 있는 문자열을 나타낸다.
	 * [\\p{L}0-9_] : 유니코드 문자(영어, 한글 등), 숫자, 언더스코어(_) 중 하나를 나타낸다.
	 * + : 1개 이상의 문자열이 나타날 수 있다.
	 *
	 * @param rawHashtags
	 */
	public void addHashtags(String rawHashtags) {
		String hashtagRegex = "(?<=\\s|^)#[\\p{L}0-9_]+";

		this.hashtags = Pattern.compile(hashtagRegex)
			.matcher(rawHashtags)
			.results()
			.map(matchResult -> matchResult.group().substring(1))
			.map(Hashtag::of)
			.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Article))
			return false;
		Article article = (Article)o;
		return id.equals(article.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
