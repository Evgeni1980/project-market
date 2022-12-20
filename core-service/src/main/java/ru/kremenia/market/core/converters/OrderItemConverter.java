package ru.kremenia.market.core.converters;

import org.springframework.stereotype.Component;
import ru.kremenia.market.api.OrderItemDto;
import ru.kremenia.market.core.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setProductId(orderItemDto.getProductId());
        orderItemDto.setProductTitle(orderItem.getProduct().getTitle());
        orderItemDto.setPrice(orderItem.getPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPricePerProduct(orderItem.getTotalPrice());
        return orderItemDto;
    }
}
