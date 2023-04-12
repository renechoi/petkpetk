package com.petkpetk.service.domain.shopping.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.petkpetk.service.domain.shopping.dto.request.ItemRequest;
import com.petkpetk.service.domain.shopping.dto.response.ItemResponse;
import com.petkpetk.service.domain.shopping.dto.security.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.security.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.security.MainItemDto;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.dto.security.ItemImageRepository;
import com.petkpetk.service.domain.shopping.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;

	@Value("${itemImageLocation}")
	private String itemImageLocation;

	private final FileService fileService;

	public Long saveItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles) {
		Item item = itemRepository.save(itemResponse.toEntity());

		System.out.println("----------------------------- item = " + item+" -----------------------------");

		List<ItemImage> itemImages = createItemImages(itemImageFiles, item);
		itemImageRepository.saveAll(itemImages);

		return item.getId();
	}

	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}

	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
		return itemRepository.getMainItemPage(itemSearchDto, pageable);
	}

	public ItemRequest getItemDetail(Long itemId) {
		List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

		List<ItemImageDto> itemImageDtos = itemImages.stream()
			.map(ItemImageDto::of)
			.collect(Collectors.toList());

		List<Long> itemImageIds = itemImages.stream().mapToLong(ItemImage::getId).boxed()
			.collect(Collectors.toList());

		Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

		System.out.println("itemRequest = " + item);
		return ItemRequest.of(item, itemImageDtos, itemImageIds);
	}

	public Long updateItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles){
		Item item = itemRepository.findById(itemResponse.getId()).orElseThrow(EntityNotFoundException::new);

		item.updateItem(itemResponse);

		// dirty checking = 변경감지
		// jpa => 객체를 갖다가 집어넣는 방식
		// jpa => transactional => 영속성 컨텍스트라는 환경을 스스로 만들어내고 그 안에서 객체들을 관리한다
		// 별도의 update 쿼리가 필요하지 않고 , 해당하는 객체의 값만 변경해주면 => transactional이 끝날때 변경을 알아서 감지해서 자동으로 업데이트를 시켜준다


		List<Long> itemImageIds = itemResponse.getItemImageIds();

		// 로컬에서 => 같은 이름으로 찾아서 삭제를 해주고
		// 다시 저장을 하고
		// 저장한 것과 동일한 것을 itemId와 매핑을 다시 해서
		// db에 저장을 해야 한다



		return 0L;

	}
	
	public int deleteItem(Long itemId){
		itemRepository.deleteById(itemId);
		itemImageRepository.deleteById(itemId);
		System.out.println("itemId = " + itemId);
		return 1;
	}




	private List<ItemImage> createItemImages(List<MultipartFile> itemImageFiles, Item item) {
		return IntStream.range(0, itemImageFiles.size())
			.mapToObj(i -> convertToItemImages(ItemImage.of(item, i), itemImageFiles.get(i)))
			.collect(Collectors.toList());
	}

	private ItemImage convertToItemImages(ItemImage itemImage, MultipartFile itemImageFile) {
		String originalImageName = itemImageFile.getOriginalFilename();
		if (StringUtils.isEmpty(originalImageName)) {
			return itemImage;
		}

		String imageName = saveAtLocal(itemImageFile, originalImageName);
		String imageUrl = "/images/item/" + imageName;
		itemImage.updateItemImage(originalImageName, imageName, imageUrl);

		return itemImage;
	}


	private String saveAtLocal(MultipartFile itemImageFile, String originalImageName) {
		try {
			return fileService.uploadFiles(itemImageLocation, originalImageName, itemImageFile.getBytes());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
