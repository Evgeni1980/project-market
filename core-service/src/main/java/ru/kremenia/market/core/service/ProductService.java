package ru.kremenia.market.core.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.kremenia.market.api.ProductDto;
import ru.kremenia.market.core.entities.Product;
import ru.kremenia.market.core.repositories.ProductRepository;
import ru.kremenia.market.core.repositories.specification.ProductSpecifications;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Page<Product> findAll(Specification<Product> spec, int page) {
        return productRepository.findAll(spec, PageRequest.of(page, 5));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product createNewProduct(ProductDto productDto) {
        Product product = new Product();
        product.setPrice(productDto.getPrice());
        product.setTitle(productDto.getTitle());
        productRepository.save(product);
        return product;
    }

    public Specification<Product> createSpecByFilters(BigDecimal minPrice, BigDecimal maxPrice, String title) {
        Specification<Product> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpecifications.priceLessThanEqualsThan(maxPrice));
        }
        if (title != null) {
            spec = spec.and(ProductSpecifications.titleLike(title));
        }
            return spec;
    }
}
