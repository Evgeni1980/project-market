package ru.kremenia.market.core.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.kremenia.market.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecifications {
    public static Specification<Product> priceGreaterOrEqualsThan(BigDecimal price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> priceLessThanEqualsThan(BigDecimal price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%S%%", titlePart)));
    }
}
