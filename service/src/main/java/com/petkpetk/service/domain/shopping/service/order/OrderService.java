package com.petkpetk.service.domain.shopping.service.order;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.dto.order.OrderDto;
import com.petkpetk.service.domain.shopping.dto.order.request.OrderRequest;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.entity.order.OrderItem;
import com.petkpetk.service.domain.shopping.repository.item.ItemImageRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.shopping.repository.order.OrderRepository;
import com.petkpetk.service.domain.user.dto.UserAccountDto;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;

	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final UserAccountRepository userAccountRepository;

	// TODO : 주문하기, 주문수정(부분), 주문취소


	/** 주문 */
	public Long createOrder(OrderRequest orderRequest, String email) {
		// 엔티티 조회
		Item item = itemRepository.findById(orderRequest.getProductId()).orElseThrow(EntityNotFoundException::new);
		UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);


		// 주문상품 생성
		List<OrderItem> orderItems = Collections.singletonList(OrderItem.from(item, item.getItemAmount()));

		// 주문 생성
		Order order = Order.from(userAccount, orderItems);
		// 주문 저장
		Order savedOrder = orderRepository.save(order);

		return savedOrder.getId();
	}

	/** 주문 취소 */
	public void cancelOrder(Long orderId) {
		orderRepository.
			findById(orderId)
			.orElseThrow(() -> new EntityNotFoundException())
			.cancelOrder();
	}

	/** 주문 검색 **/
	public List<OrderDto> searchOrders(String userAccountName, LocalDateTime startDate, LocalDateTime endDate, OrderStatus status) {
		List<Order> orders = orderRepository.searchOrders(userAccountName, startDate, endDate, status);
		return orders.stream()
			.map(OrderDto::fromEntity)
			.collect(Collectors.toList());
	}

}
