package ru.kremenia.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kremenia.market.api.OrderDto;
import ru.kremenia.market.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setItems(order.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()));
        orderDto.setUsername(order.getUsername());
        orderDto.setTotalPrice(order.getTotalPrice());
        return orderDto;
    }
}
