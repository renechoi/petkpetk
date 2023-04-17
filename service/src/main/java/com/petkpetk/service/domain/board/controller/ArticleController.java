package com.petkpetk.service.domain.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.petkpetk.service.domain.board.dto.ArticleDto;
import com.petkpetk.service.domain.board.service.ArticleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping("/save")
	public String saveForm() {
		return "save";
	}

	@PostMapping("/save")
	public String save(@ModelAttribute ArticleDto articleDto) {
		System.out.println("articleDto = " + articleDto);
		articleService.save(articleDto);
		return "index";
	}

	@GetMapping("/")
	public String findAll(Model model) {
		List<ArticleDto> articleDtoList = articleService.findAll();
		model.addAttribute("articleList", articleDtoList);
		return "list";
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable Long id, Model model) {
		articleService.updateHits(id);
		ArticleDto articleDto = articleService.findById(id);
		model.addAttribute("article", articleDto);
		return "detail";
	}

	@GetMapping("/update/{id}")
	public String updateForm(@PathVariable Long id, Model model) {
		ArticleDto articleDto = articleService.findById(id);
		model.addAttribute("articleUpdate", articleDto);
		return "update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute ArticleDto articleDto, Model model) {
		ArticleDto articleDtoUpdate = articleService.update(articleDto);
		model.addAttribute("articleDtoUpdate", articleDtoUpdate);
		return "detail";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		articleService.delete(id);
		return "redirect:/article/";
	}
}
