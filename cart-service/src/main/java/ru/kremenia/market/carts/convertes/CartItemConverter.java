package ru.kremenia.market.carts.convertes;

import org.springframework.stereotype.Component;
import ru.kremenia.market.api.CartItemDto;
import ru.kremenia.market.carts.model.CartItem;

@Component
public class  CartItemConverter {

    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}
