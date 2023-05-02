package com.petkpetk.service.domain.community.service;

import static com.petkpetk.service.domain.community.constatnt.SearchType.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true)
	public Page<ArticleDto> searchArticles(SearchType searchType, String searchValue, Pageable pageable) {

		if (searchValue == null || searchValue.isBlank()) {
			return articleRepository.findAll(pageable).map(this::convertToDto);

		}

		if (searchType == TITLE) {
			return articleRepository.findByTitleContaining(searchValue, pageable).map(this::convertToDto);
		}
		// todo : 다른 검색 조건에 대한 조회 기능 추가 구현 필요 + 분기를 사용하지 않도록 리팩토링 + 리턴 값 정리
		return null;
	}

	@Transactional(readOnly = true)
	public ArticleDto searchArticle(Long articleId) {
		return articleRepository.findById(articleId)
			.map(this::convertToDto)
			.orElseThrow(ArticleNotFoundException::new);
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

}




