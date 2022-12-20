package ru.kremenia.market.core.converters;

import org.springframework.stereotype.Component;
import ru.kremenia.market.api.ProductDto;
import ru.kremenia.market.core.entities.Product;

@Component
public class ProductConverter {

    public ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setCategoryTitle(product.getCategory().getTitle());
        return productDto;
    }

    public Product DtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getPrice());
    }
}
