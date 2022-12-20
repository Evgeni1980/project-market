package ru.kremenia.market.core.integration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kremenia.market.api.CartDto;
import ru.kremenia.market.core.exceptions.ResourceNotFoundException;

@Component
@AllArgsConstructor
public class CartServiceIntegration {
    private final WebClient productServiceWebClient;

    public CartDto getCart(String username) {
        return productServiceWebClient.get()
                .uri("/api/v1/cart/" + username)
                .header("username", username)
                .retrieve()
                .onStatus(httpStatus -> httpStatus.value()== HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Can't make order")))
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clear(String username) {
        productServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
