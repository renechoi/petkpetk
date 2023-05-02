package com.petkpetk.service.domain.shopping.service.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.config.converter.ImageConverter;
import com.petkpetk.service.config.file.ImageLocalRepository;
import com.petkpetk.service.domain.shopping.dto.item.ItemDto;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.item.ItemSearchDto;
import com.petkpetk.service.domain.shopping.dto.item.MainItemDto;
import com.petkpetk.service.domain.shopping.dto.item.ManageItemDto;
import com.petkpetk.service.domain.shopping.dto.item.request.ItemRegisterRequest;
import com.petkpetk.service.domain.shopping.dto.item.response.ItemResponse;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.entity.review.Review;
import com.petkpetk.service.domain.shopping.exception.ItemNotFoundException;
import com.petkpetk.service.domain.shopping.repository.item.ItemImageRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.shopping.repository.review.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
	private final ItemRepository itemRepository;
	private final ReviewRepository reviewRepository;
	private final ItemImageRepository itemImageRepository;
	private final ImageLocalRepository<ItemImage> imageLocalRepository;


	@Value("${LOCAL_ITEM_IMAGE_UPLOAD_PATH}")
	private String filePath;


	@Transactional(readOnly = true)
	public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		return itemRepository.getMainItemPage(itemSearchDto, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ManageItemDto> getItemList(ItemSearchDto itemSearchDto, Pageable pageable,
		String email) {
		return itemRepository.getManageList(itemSearchDto, pageable, email);
	}

	public ItemResponse getItemDetail(Long itemId) {
		return ItemResponse.from(findById(itemId));
	}

	public void registerItem(ItemDto itemDto) {
		List<ItemImage> images = ImageConverter.of(ItemImage::from)
			.convertToImages(itemDto.getRawImages());

		// 영속화 
		itemRepository.save(itemDto.toEntity(images));

		// local 저장
		IntStream.range(0, images.size())
			.forEach(image -> imageLocalRepository.save(images.get(image),
				itemDto.getRawImages().get(image)));
	}

	public ItemResponse updateItem(ItemRegisterRequest itemUpdateRequest) {

		Item item = itemRepository.findById(itemUpdateRequest.getId())
			.orElseThrow(ItemNotFoundException::new);
		List<ItemImage> itemImages = item.getImages();
		itemUpdateRequest.getImages().removeIf(MultipartFile::isEmpty);

		List<ItemImage> notModifiedImages = itemUpdateRequest.getItemImageDtos()
			.stream()
			.map(ItemImageDto::getUniqueName)
			.map(itemImageRepository::findByUniqueName)
			.collect(Collectors.toList());

		List<ItemImage> newlyAddedImages = itemUpdateRequest.getImages().stream()
			.map(image -> itemUpdateRequest.getImages().indexOf(image) == 0 && !image.isEmpty()
				? ItemImage.asRepresentative(image)
				: ItemImage.from(image))
			.collect(Collectors.toList());

		List<ItemImage> allRequestedImages = Stream.concat(notModifiedImages.stream(),
				newlyAddedImages.stream())
			.collect(Collectors.toList());

		// todo : itemImages(DB) 중에서 allRequestedImages 없으면 삭제 대상.
		List<ItemImage> imagesToDelete = itemImages.stream()
			.filter(itemImage -> !allRequestedImages.contains(itemImage))
			.collect(Collectors.toList());

		imageLocalRepository.deleteFiles(imagesToDelete);

		IntStream.range(0, newlyAddedImages.size())
			.forEach(image -> imageLocalRepository.save(newlyAddedImages.get(image),
				itemUpdateRequest.getImages().get(image)));

		// todo: db에서 가져온 itemImages 중에 notModifiedImages에 해당하는 itemImage가 없으면 삭제 대상
		itemImages.removeIf(itemImage -> !notModifiedImages.contains(itemImage));

		// todo : newlyAddedImages 는 itemImages에 추가
		itemImages.addAll(newlyAddedImages);
		item.mapImages(itemImages);

		item.setContents(itemUpdateRequest);

		return ItemResponse.from(item);
	}

	public void deleteItem(Long itemId) {
		Item item = findById(itemId);
		item.setDeletedYn("Y");

		List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
		itemImages.forEach(itemImage -> itemImage.setDeletedYn("Y"));

		IntStream.range(0, itemImages.size())
			.forEach(i ->
				imageLocalRepository.deleteFile(filePath, itemImages.get(i).getUniqueName())
			);
	}

	private Item findById(Long itemId) {
		return itemRepository.findById(itemId).orElseThrow(ItemNotFoundException::new);
	}

	private List<ItemImage> findByItemIdOrderByIdAsc(Long id) {
		return itemImageRepository.findByItemIdOrderByIdAsc(id);
	}

	public Item getItem(Long id) {
		return itemRepository.findById(id).get();
	}

	public List<Item> getReviewItemList(List<Review> reviewList) {

		List<Item> itemList = new ArrayList<>();
		reviewList.forEach(
			review -> itemList.add(itemRepository.findById(review.getItem().getId()).get()));

		return itemList;
	}

	public List<ItemImage> finditemImageUrls(List<Long> itemIds) {
		List<ItemImage> itemImages = new ArrayList<>();
		IntStream.range(0, (itemIds.size()))
			.forEach( i ->
				itemImages.add(itemImageRepository.findAllByItem_Id(itemIds.get(i)))
			);

		return itemImages;
	}

	public Long getItemCount() {
		return itemRepository.getItemCount();
	}
}
