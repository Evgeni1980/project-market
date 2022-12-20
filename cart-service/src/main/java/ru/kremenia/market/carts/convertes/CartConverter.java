package ru.kremenia.market.carts.convertes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kremenia.market.api.CartDto;
import ru.kremenia.market.carts.model.Cart;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart c) {
        return new CartDto(c.getItems()
                .stream()
                .map(cartItemConverter::entityToDto)
                .collect(Collectors.toList()), c.getTotalPrice());
    }
}
