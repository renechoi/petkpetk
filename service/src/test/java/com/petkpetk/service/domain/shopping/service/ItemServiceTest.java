package com.petkpetk.service.domain.shopping.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.service.item.ItemService;

@Transactional
@SpringBootTest
class ItemServiceTest {

	 @Autowired
	 ItemService itemService;

	public MultipartFile createMultipartFile(String filePath) throws IOException {
		// 파일 객체를 생성함
		File file = new File(filePath);

		// 파일 이름과 내용을 읽어옴
		String fileName = file.getName();
		byte[] fileContent = Files.readAllBytes(file.toPath());

		// CommonsMultipartFile 객체를 생성하여 반환함

		return new MockMultipartFile(fileName, filePath, "multipart/form-data", fileContent);
	}
	// 상품 등록
	@Test
	void addItem() throws IOException {



		ItemResponse itemResponse = ItemResponse.of(
			Item.of("개껌", 1000L, 20L, "사람 간식으로 좋은", ItemStatus.SELL, LocalDateTime.now(),
				LocalDateTime.now()));

		String filePath = "C:\\KYE\\sample-project\\src\\main\\resources\\static\\img\\cat.png";

		MultipartFile multipartFile = createMultipartFile(filePath);

		List<MultipartFile> multipartFiles = new ArrayList<>();
		multipartFiles.add(multipartFile);

		Long num = itemService.saveItem(itemResponse, multipartFiles);
		System.out.println("num = " + num);
	}

	@Test
	void deleteItem() {
		itemService.deleteItem(1L);

	}




}