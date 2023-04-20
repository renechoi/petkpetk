package com.petkpetk.service.config.converter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.common.PetkpetkImage;

/**
 * Multipart 이미지 파일을 ItemImage, ProfileImage 등 PetkpetkImage를 구현한 Image 객체로 생성하는 Converter.
 * 예시 코드는 아래와 같다.
 *
 * ImageConverter<PetkpetkImage> itemImageConverter = new ImageConverter<>(ItemImage::from);
 * List<PetkpetkImage> convertedImages = itemImageConverter.convertToImages(rawImages);
 *
 * ImageConverter<ItemImage> itemImageConverter = new ImageConverter<>(ItemImage::new);
 * List<ItemImage> convertedImages = itemImageConverter.convertToImages(rawImages);
 *
 * ImageConverter<ProfileImage> profileImageConverter = new ImageConverter<>(ProfileImage::new);
 * List<ProfileImage> convertedImages = profileImageConverter.convertToImages(rawImages);
 * @param <T>
 */

public class ImageConverter<T extends PetkpetkImage> {

    private final Function<MultipartFile, T> imageConstructor;

    public ImageConverter(Function<MultipartFile, T> imageConstructor) {
        this.imageConstructor = imageConstructor;
    }

    public List<T> convertToImages(List<MultipartFile> rawImages) {
        return rawImages.stream()
                .filter(image -> !image.isEmpty())
                .map(imageConstructor)
                .collect(Collectors.toList());
    }
}
