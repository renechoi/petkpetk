package com.petkpetk.service.domain.shopping.service.order;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petkpetk.service.domain.shopping.constant.OrderStatus;
import com.petkpetk.service.domain.shopping.dto.order.OrderDto;
import com.petkpetk.service.domain.shopping.dto.order.OrderHistoryDto;
import com.petkpetk.service.domain.shopping.dto.order.OrderItemDto;
import com.petkpetk.service.domain.shopping.dto.order.request.CheckoutRequest;
import com.petkpetk.service.domain.shopping.dto.order.request.OrderRequest;
import com.petkpetk.service.domain.shopping.dto.order.response.CheckoutResponse;
import com.petkpetk.service.domain.shopping.dto.priceInfo.CheckoutPriceInfo;
import com.petkpetk.service.domain.shopping.entity.delivery.Delivery;
import com.petkpetk.service.domain.shopping.entity.item.Item;
import com.petkpetk.service.domain.shopping.entity.item.ItemImage;
import com.petkpetk.service.domain.shopping.entity.order.Order;
import com.petkpetk.service.domain.shopping.entity.order.OrderItem;
import com.petkpetk.service.domain.shopping.exception.OrderAlreadyInProcessException;
import com.petkpetk.service.domain.shopping.repository.item.ItemImageRepository;
import com.petkpetk.service.domain.shopping.repository.item.ItemRepository;
import com.petkpetk.service.domain.shopping.repository.order.OrderRepository;
import com.petkpetk.service.domain.shopping.service.item.ItemService;
import com.petkpetk.service.domain.user.entity.UserAccount;
import com.petkpetk.service.domain.user.entity.embedded.Address;
import com.petkpetk.service.domain.user.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;

	private final ItemRepository itemRepository;
	private final ItemService itemService;
	private final ItemImageRepository itemImageRepository;
	private final UserAccountRepository userAccountRepository;

	// TODO : 주문하기, 주문수정(부분), 주문취소


	/** 주문 */
	public Long createOrder(OrderRequest orderRequest, String email) {
		// 엔티티 조회
		Item item = itemService.getItem(orderRequest.getItemId());
		UserAccount userAccount = userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);

		// 주문상품 생성
		List<OrderItem> orderItems = Collections.singletonList(OrderItem.from(item, item.getItemAmount()));

		// 주문 생성
		Order order = Order.from(userAccount, orderItems);
		order.createDelivery(new Delivery(userAccount.getAddress()));

		// 주문 저장
		Order savedOrder = orderRepository.save(order);

		return savedOrder.getId();
	}

	public Long createOrders(List<OrderDto> orderDtoList, String email) {
		UserAccount userAccount = findUserAccountByEmail(email);
		List<OrderItem> orderItems = createOrderItems(orderDtoList, OrderStatus.ORDER);

		Order order = Order.createOrder(userAccount,new Delivery(Address.of("123","서울시","금천구","가산동")), orderItems);
		Order savedOrder = orderRepository.save(order);
		return savedOrder.getId();
	}

	private List<OrderItem> createOrderItems(List<OrderDto> orderDtoList, OrderStatus orderStatus) {
		return orderDtoList.stream()
			.map(orderDto -> {
				Item item = itemRepository.findById(orderDto.getItemId())
					.orElseThrow(EntityNotFoundException::new);
				return OrderItem.createOrderItem(item, orderDto.getOrderCount(),  OrderStatus.ORDER);

			})
			.collect(Collectors.toList());
	}

	/** 주문 취소 */
	public void cancelOrder(Order order, Delivery delivery ) {
		// TODO : 주문취소 ... => 영속화된 주문을 n 으로 바꿔주기 + 연관관계 매핑된 애들도 n으로 바꿔주기
		// orderstatus -> cancel로 변경

		if (order.isDeliveryInProcess()) {
			throw new OrderAlreadyInProcessException();
		}
		order.cancel();
		delivery.cancelDelivery();

	}

	/** 주문 검색 **/
	public List<OrderDto> searchOrders(String userAccountName, LocalDateTime startDate, LocalDateTime endDate, OrderStatus status) {
		List<Order> orders = orderRepository.searchOrders(userAccountName, startDate, endDate, status);

		for (Order order:orders) {
			System.out.println( "******************* 주문 검색 주문 아이템s 테스트 " + order);
		}


		return orders.stream()
			.map(OrderDto::fromEntity)
			.collect(Collectors.toList());
	}

	/** 주문 유효성 검사 */
	@Transactional(readOnly = true)
	public boolean validateOrder(Long orderId, String email) {
		UserAccount currentUser = findUserAccountByEmail(email);
		Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
		UserAccount savedUser = order.getUserAccount();

		return (currentUser == savedUser);
	}

	private UserAccount findUserAccountByEmail(String email) {
		return userAccountRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
	}



	/** order history 관련 */
	public Page<OrderHistoryDto> getOrders(String email, Pageable pageable) {
		List<OrderHistoryDto> orderHistoryDtos =
			orderRepository.findOrders(email, pageable).stream()
				.map(this::createOrderHistoryDto)
				.collect(Collectors.toList());

		Long totalOrderCounts = orderRepository.countOrder(email);
		return new PageImpl<>(orderHistoryDtos, pageable, totalOrderCounts);
	}

	private OrderHistoryDto createOrderHistoryDto(Order order) {
		OrderHistoryDto orderHistoryDto = new OrderHistoryDto(order);
		orderHistoryDto.setOrderItemDtos(createOrderItemDtos(order));
		return orderHistoryDto;
	}

	private List<OrderItemDto> createOrderItemDtos(Order order) {
		List<OrderItem> orderItems = order.getOrderItems();
		return orderItems.stream()
			.map(this::createOrderItemDto)
			.collect(Collectors.toList());
	}

	private OrderItemDto createOrderItemDto(OrderItem orderItem) {
		ItemImage itemImage = itemImageRepository.findByItemIdAndRepresentativeImageYn(orderItem.getItem().getId(), "Y");
		String imageUrl = itemImage.getImageUrl();
		return new OrderItemDto(orderItem, imageUrl);
	}

	public CheckoutResponse createCheckOut(CheckoutRequest checkoutRequest) {
		checkoutRequest.getCheckoutDtos()
			.forEach(checkoutDto -> checkoutDto.update(itemService.searchItem(checkoutDto.getItemId())));

		return CheckoutResponse.of(checkoutRequest.getCheckoutDtos(), CheckoutPriceInfo.of(checkoutRequest));
	}



}
