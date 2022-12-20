package ru.kremenia.market.carts.integration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.kremenia.market.api.ProductDto;
import ru.kremenia.market.carts.exeptions.ResourceNotFoundException;

@Component
@AllArgsConstructor
public class ProductServiceIntegration {
    private final WebClient productServiceWebClient;

    public ProductDto getProductById(Long id) {
        return productServiceWebClient.get()
                .uri("/api/v1/products/" + id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.value() == HttpStatus.NOT_FOUND.value(),
                        clientResponse -> Mono.error(new ResourceNotFoundException("Продукт не найден"))
                        )
                .bodyToMono(ProductDto.class)
                .block();
    }
}
