package com.petkpetk.service.domain.community.service;

import static com.petkpetk.service.domain.community.constatnt.SearchType.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.common.CategoryType;
import com.petkpetk.service.domain.community.constatnt.SearchType;
import com.petkpetk.service.domain.community.dto.ArticleDto;
import com.petkpetk.service.domain.community.entity.Article;
import com.petkpetk.service.domain.community.entity.ArticleImage;
import com.petkpetk.service.domain.community.entity.Hashtag;
import com.petkpetk.service.domain.community.exception.ArticleNotFoundException;
import com.petkpetk.service.domain.community.repository.ArticleRepository;
import com.petkpetk.service.domain.community.repository.HashtagRepository;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
	private final ArticleRepository articleRepository;
	private final UserAccountRepository userAccountRepository;
	private final articleImageService articleImageService;
	private final HashtagRepository hashtagRepository;

	/**
	 * todo: if문 분기에 대한 추후 리팩토링을 검토해볼 필요가 있다.
	 * 현재와 같은 수준에서는 if문을 분기하는 방식을 고수하되, 검색 타입이 늘어나는 경우 SearchType enum 클래스와 함수형 인터페이스를 이용해
	 * 메서드를 던져주는 방식으로 리팩토링을 고려해보자.
	 */
	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType searchType, String searchValue, Pageable pageable) {

		if (searchType == TITLE) {
			return articleRepository.findByTitleContaining(searchValue, pageable).map(this::convertToDto);
		}

		if (searchType == CONTENT) {
			return articleRepository.findByContentContaining(searchValue, pageable).map(this::convertToDto);
		}

		if (searchType == NICKNAME) {
			return articleRepository.findByUserAccount_NicknameContaining(searchValue, pageable)
				.map(this::convertToDto);
		}

		if (searchType == HASHTAG) {
			return articleRepository.findByHashtagNames(Set.of(searchValue), pageable).map(this::convertToDto);
		}

		if (searchType == CATEGORY) {
			Set<CategoryType> categoryTypes = Arrays.stream(searchValue.split(","))
				.map(value -> CategoryType.valueOf(value.trim()))
				.collect(Collectors.toSet());

			return articleRepository.findByCategoryTypeIn(categoryTypes, pageable).map(this::convertToDto);
		}

		return articleRepository.findAll(pageable).map(this::convertToDto);
	}

	public ArticleDto searchArticle(Long articleId) {
		Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
		article.setHit(article.getHit() + 1);
		return convertToDto(article);
	}

	public void saveArticle(ArticleDto articleDto) {
		UserAccount userAccount = userAccountRepository.findByEmail(articleDto.getUserAccountDto().getEmail())
			.orElseThrow(ArticleNotFoundException::new);

		List<ArticleImage> images = articleImageService.convertToImages(articleDto);

		articleRepository.save(articleDto.toEntity(userAccount, images));

		articleImageService.uploadImages(articleDto, images);
	}

	public void deleteArticle(Long articleId) {
		Article article = articleRepository.getReferenceById(articleId);
		article.setDeletedYn("Y");
		articleRepository.flush();

		deleteHashtagsByArticle(article);
		articleImageService.deleteImagesByArticle(articleId);
	}

	private void deleteHashtagsByArticle(Article article) {
		Set<Long> hashtagIds = article.getHashtags()
			.stream()
			.map(Hashtag::getId)
			.collect(Collectors.toUnmodifiableSet());
		hashtagIds.forEach(this::deleteHashtagByArticle);
	}

	private void deleteHashtagByArticle(Long hashtagId) {
		Hashtag hashtag = hashtagRepository.getReferenceById(hashtagId);
		if (hashtag.getArticles().stream().allMatch(this::isArticleDeleted)) {
			hashtagRepository.delete(hashtag);
		}
	}

	private ArticleDto convertToDto(Article article) {
		return ArticleDto.from(article, articleImageService.convertToRawImages(article.getArticleImages()));
	}

	private boolean isArticleDeleted(Article article) {
		return articleRepository.getReferenceById(article.getId()).getDeletedYn().equals("Y");
	}

	public Long getArticleLastId() {
		return articleRepository.findTopByOrderByIdDesc().getId();
	}

	public List<String> getHashtags() {
		return hashtagRepository.findAllHashtagNames();
	}

	public int getArticleTotalCount() {return  articleRepository.findAll().size();}
}
