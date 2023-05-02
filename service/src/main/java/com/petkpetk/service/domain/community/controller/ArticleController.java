package com.petkpetk.service.domain.community.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.petkpetk.service.domain.community.constatnt.SearchType;
import com.petkpetk.service.domain.community.dto.ArticleDto;
import com.petkpetk.service.domain.community.dto.request.ArticlePostRequest;
import com.petkpetk.service.domain.community.dto.response.ArticleResponse;
import com.petkpetk.service.domain.community.service.ArticleService;
import com.petkpetk.service.domain.community.service.PaginationService;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.dto.security.UserAccountPrincipal;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/community/articles")
public class ArticleController {

	private final ArticleService articleService;
	private final PaginationService paginationService;

	@GetMapping
	public String articles(@RequestParam(required = false) SearchType searchType,
		@RequestParam(required = false) String searchValue,
		@PageableDefault(size = 7, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable, Model model) {
		Page<ArticleResponse> articles = articleService.searchArticles(searchType, searchValue, pageable)
			.map(ArticleResponse::from);
		List<Integer> pageBars = paginationService.getPageBars(pageable.getPageNumber(), articles.getTotalPages());

		model.addAttribute("articles", articles);
		model.addAttribute("pageBars", pageBars);
		model.addAttribute("searchTypes", SearchType.values());
		model.addAttribute("searchTypeHashtag", SearchType.HASHTAG);

		return "/community/article/articles";
	}

	@GetMapping("/{articleId}")
	public String article(@PathVariable Long articleId, Model model) {
		ArticleResponse article = ArticleResponse.from(articleService.searchArticle(articleId));

		model.addAttribute("article", article);
		model.addAttribute("lastArticleId", articleService.getArticleLastId());
		model.addAttribute("searchTypeHashtag", SearchType.HASHTAG);

		return "/community/article/detail";
	}

	@GetMapping("/post")
	public String postArticle(Model model) {
		model.addAttribute("article", new ArticlePostRequest());
		return "/community/article/post";
	}

	@PostMapping("/post")
	public String postArticle(@Valid ArticlePostRequest articlePostRequest,
		@AuthenticationPrincipal UserAccountPrincipal userAccountPrincipal) {

		articleService.saveArticle(ArticleDto.from(articlePostRequest, UserAccountDto.from(userAccountPrincipal)));

		return "redirect:/";
	}

	@PostMapping("/{articleId}/delete")
	public String deleteArticle(@PathVariable Long articleId) {
		articleService.deleteArticle(articleId);

		return "redirect:/articles";
	}

}
