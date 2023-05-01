package com.petkpetk.service.domain.community.dto.request;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.common.CategoryType;

import lombok.Data;

@Data
public class ArticlePostRequest {

	private String title;
	private String content;
	private String rawHashtags;
	private List<MultipartFile> rawImages = new ArrayList<>();
	private CategoryType categoryType;
}
