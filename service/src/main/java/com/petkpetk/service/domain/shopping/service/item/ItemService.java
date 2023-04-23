package com.petkpetk.service.domain.shopping.service.item;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.item.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.item.MainItemDto;
import com.petkpetk.service.domain.shopping.dto.item.ManageItemDto;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRequest;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.repository.item.ItemImageRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.shopping.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Value("${itemImageLocation}")
	String itemImageLocation;

	private final FileService fileService;

	/*======================= 아이템 등록 =======================*/
	public Long saveItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles) {

		System.out.println(
			"---------- itemResponse = " + itemResponse + " ----------");
		System.out.println(
			"---------- itemImageFiles = " + itemImageFiles + " ----------");

		for (int i = 0; i < itemImageFiles.size(); i++) {
			System.out.println("itemImageFiles = " + itemImageFiles.get(i).getOriginalFilename());
		}

		Item item = itemRepository.save(itemResponse.toEntity());

		System.out.println(
			"----------------------------- itemDto = " + item + " -----------------------------");
		System.out.println(
			"----------------------------- itemImageFiles.size() = " + itemImageFiles.size());

		// 이미지 등록 안한 input은 테이블에 등록시키지 않아 데이터를 최소화한다.
		List<MultipartFile> filterImagesList = new ArrayList<>();
		for (int i = 0; i < itemImageFiles.size(); i++) {
			if (!itemImageFiles.get(i).getOriginalFilename().isEmpty()) {
				filterImagesList.add(itemImageFiles.get(i));

			} else {
				System.out.println("===========================");
				System.out.println("========= 이미지 없음 ==========");
				System.out.println("===========================");
			}
		}

		System.out.println("============================= filterImagesList = " + filterImagesList);

		List<ItemImage> itemImages = createItemImages(filterImagesList, item, 0);
		itemImageRepository.saveAll(itemImages);

		return item.getId();
	}

	@Transactional(readOnly = true)
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getAdminItemPage(itemSearchDto, pageable);
	}

	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getMainItemPage(itemSearchDto, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ManageItemDto> getItemList(ItemSearchDto itemSearchDto, Pageable pageable) {
		System.out.println("itemSearchDto = " + itemSearchDto);
		System.out.println("pageable = " + pageable);
		return itemRepository.getManageList(itemSearchDto, pageable);
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

	/*======================= 아이템 수정 =======================*/
	@Transactional
	public ItemRequest updateItem(ItemResponse itemResponse, List<MultipartFile> itemImageFiles,
		Long itemId, List<String> imageNames) {

		Item item = entityManager.find(Item.class, itemId);
		item.updateItem(itemResponse);
		entityManager.flush();

		// 이미지 변한 사항 없을 때
		if (itemImageFiles == null) {
			List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
			List<ItemImage> deleteItemImages = new ArrayList<>();
			List<Long> deleteImageIds = new ArrayList<>();

			if (imageNames.size() < itemImages.size()) {
				for (int i = 0; i < itemImages.size(); i++) {
					if (i > imageNames.size() - 1) {
						deleteImageIds.add(itemImages.get(i).getId());
						deleteItemImages.add(itemImages.get(i));
					}
				}
			}

			itemImageRepository.deleteAllById(deleteImageIds);
			getDeleteItemImages(deleteItemImages);

			List<ItemImage> newitemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

			List<ItemImageDto> itemImageDtos = newitemImages.stream()
				.map(ItemImageDto::of)
				.collect(Collectors.toList());

			List<Long> imageIds = newitemImages.stream().mapToLong(ItemImage::getId).boxed()
				.collect(Collectors.toList());

			return ItemRequest.of(item, itemImageDtos, imageIds);

		} else {


			List<MultipartFile> filterImagesList = new ArrayList<>();
			for (MultipartFile imageFile : itemImageFiles) {
				if (!imageFile.getOriginalFilename().isEmpty()) {
					filterImagesList.add(imageFile);

				} else {
					System.out.println("===========================");
					System.out.println("========= 이미지 없음 ==========");
					System.out.println("===========================");
				}
			}

			List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

			if (imageNames.size() < itemImages.size()) {
				List<ItemImage> deleteImages = new ArrayList<>();
				List<Long> deleteImageIds = new ArrayList<>();
				int num = 0;
				for (int i = 0; i < itemImages.size(); i++) {
					if (i > imageNames.size() - 1) {
						if (itemImages.get(i).getImageName().equals(imageNames.get(i))) {
							if (i == 0) {
								num = 1;
							}

						} else {
							if (i == 0) {
								num = 0;
							}
							itemImages.get(i).setRepresentativeImageYn("N");
							deleteImageIds.add(itemImages.get(i).getId());
							deleteImages.add(itemImages.get(i));
						}
					}
				}
				itemImageRepository.saveAll(itemImages);

				itemImageRepository.deleteAllById(deleteImageIds);
				getDeleteItemImages(deleteImages);

				List<ItemImage> newitemImages = createItemImages(filterImagesList, item, num);


				itemImageRepository.saveAll(newitemImages);

				List<ItemImage> finalNewImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

				List<ItemImageDto> itemImageDtos = finalNewImages.stream()
					.map(ItemImageDto::of)
					.collect(Collectors.toList());

				List<Long> newitemImageIds = finalNewImages.stream().mapToLong(ItemImage::getId).boxed()
					.collect(Collectors.toList());

				return ItemRequest.of(item, itemImageDtos, newitemImageIds);

			}else {

				List<ItemImage> deleteImages = new ArrayList<>();
				List<Long> deleteImageIds = new ArrayList<>();
				int num = 0;
				for (int i = 0; i < imageNames.size(); i++) {
					if (itemImages.get(i).getImageName().equals(imageNames.get(i))) {
						if (i == 0) {
							num = 1;
						}

					} else {
						if (i == 0) {
							num = 0;
						}
						itemImages.get(i).setRepresentativeImageYn("N");
						deleteImageIds.add(itemImages.get(i).getId());
						deleteImages.add(itemImages.get(i));
					}
				}
				itemImageRepository.saveAll(itemImages);

				itemImageRepository.deleteAllById(deleteImageIds);
				getDeleteItemImages(deleteImages);

				List<ItemImage> newitemImages = createItemImages(filterImagesList, item, num);


				itemImageRepository.saveAll(newitemImages);

				List<ItemImage> finalNewImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

				List<ItemImageDto> itemImageDtos = finalNewImages.stream()
					.map(ItemImageDto::of)
					.collect(Collectors.toList());

				List<Long> newitemImageIds = finalNewImages.stream().mapToLong(ItemImage::getId).boxed()
					.collect(Collectors.toList());

				return ItemRequest.of(item, itemImageDtos, newitemImageIds);
			}
		}
	}

	public void getDeleteItemImages(List<ItemImage> itemImages) {
		deletePathImage(itemImages);
	}

	/*======================= 아이템 삭제 =======================*/
	public List<ItemImage> deleteItem(Long itemId) {

		// UploadPath 에 있는 해당 이미지들도 originalImageName으로 모두 삭제
		List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

		// item deletedYn 을 "Y" 로 수정
		Item item = itemRepository.findById(itemId).get();
		System.out.println("item = " + item);

		item.setDeletedYn("Y");
		System.out.println("item = " + item);

		ItemResponse itemResponse = ItemResponse.of(item);
		item.updateItem(itemResponse);
		itemRepository.save(item);

		// itemImage 에서 item_id = itemId 를 갖고 있는 이미지들 모두 삭제
		List<Long> itemImageIds = itemImages.stream().mapToLong(ItemImage::getId).boxed()
			.collect(Collectors.toList());
		System.out.println("itemImageIds = " + itemImageIds);
		itemImageRepository.deleteAllById(itemImageIds);

		return itemImages;
	}

	public List<ItemImage> getImageFiles(Long itemId) {
		List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
		return itemImages;
	}

	public void deletePathImage(List<ItemImage> itemImages) {
		List<String> originalImageName = itemImages.stream()
			.map(ItemImage::getOriginalImageName)
			.collect(Collectors.toList());

		originalImageName.forEach(i -> deleteAtLocal(i));
	}

	private void deleteItemImages(List<MultipartFile> itemImageFiles) {
		itemImageFiles.forEach(file -> {
			makeDelete(file);
		});
	}

	private void makeDelete(MultipartFile itemImageFiles) {
		String originalImageName = itemImageFiles.getOriginalFilename();
		deleteAtLocal(originalImageName);
	}

	// ================================================================
	private List<ItemImage> createItemImages(List<MultipartFile> itemImageFiles, Item item,
		int num) {
		if (num == 0) {

			return IntStream.range(0, itemImageFiles.size())
				.mapToObj(i -> convertToItemImages(ItemImage.of(item, i), itemImageFiles.get(i)))
				.collect(Collectors.toList());
		} else {

			return IntStream.range(0, itemImageFiles.size())
				.mapToObj(i -> convertToItemImages(ItemImage.of(item, num), itemImageFiles.get(i)))
				.collect(Collectors.toList());
		}

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
			return fileService.uploadFiles(itemImageLocation, originalImageName,
				itemImageFile.getBytes());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	private void deleteAtLocal(String originalImageName) {
		try {
			fileService.deleteFile(itemImageLocation, originalImageName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
