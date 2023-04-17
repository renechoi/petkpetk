package com.petkpetk.service.domain.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.petkpetk.service.domain.board.dto.ArticleDto;
import com.petkpetk.service.domain.board.entity.Article;
import com.petkpetk.service.domain.board.repository.ArticleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;

	public void save(ArticleDto articleDto) {
		Article article = articleDto.toEntity();
		// Article article = Article.toSaveEntity(articleDto);
		articleRepository.save(article);
	}

	public List<ArticleDto> findAll() {
		List<Article> articleEntityList = articleRepository.findAll();
		List<ArticleDto> articleDtoList = new ArrayList<>();
		for(Article article : articleEntityList) {
			// articleDtoList.add(ArticleDto.toArticleDto(article));
			articleDtoList.add(ArticleDto.fromEntity(article));
		}
		return articleDtoList;
	}

	@Transactional
	public void updateHits(Long id) {
		articleRepository.updateHits(id);
	}

	public ArticleDto findById(Long id) {
		Optional<Article> optionalArticle = articleRepository.findById(id);
		if(optionalArticle.isPresent()){
			Article article = optionalArticle.get();
			// ArticleDto articleDto = ArticleDto.toArticleDto(article);
			ArticleDto articleDto = ArticleDto.fromEntity(article);
			return articleDto;
		}else {
			return null;
		}
	}

	public ArticleDto update(ArticleDto articleDto) {
		Article article = Article.toUpdateEntity(articleDto);
		articleRepository.save(article);
		return findById(article.getArticleId());
	}

	public void delete(Long id) {
		articleRepository.deleteById(id);
	}
}
