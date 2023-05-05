package com.petkpetk.service.domain.shopping.dto.order.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.petkpetk.service.domain.shopping.constant.ItemStatus;
import com.petkpetk.service.domain.shopping.dto.item.ItemImageDto;
import com.petkpetk.service.domain.shopping.dto.order.CheckoutDto;
import com.petkpetk.service.domain.user.dto.UserAccountDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutResponse {

	private List<CheckoutDto> checkoutDtos;

	public static CheckoutResponse of(List<CheckoutDto> checkoutDtos) {
		return new CheckoutResponse(checkoutDtos);
	}
}
