package ru.kremenia.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.kremenia.market.api.ProductDto;
import ru.kremenia.market.carts.integration.ProductServiceIntegration;
import ru.kremenia.market.carts.model.Cart;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }


    public void addToCart(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        execute(uuid, cart -> cart.add(product));
    }

    public void remove(String uuid, Long productId) {
        execute(uuid, cart -> cart.remove(productId));
    }

    public void clearCart(String uuid) {
        execute(uuid, Cart::clear);
    }

    private void execute (String uuid, Consumer<Cart> operation) {
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(cartPrefix + uuid, cart);
    }

}
