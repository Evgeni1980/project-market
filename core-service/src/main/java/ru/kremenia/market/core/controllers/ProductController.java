package ru.kremenia.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kremenia.market.api.PageDto;
import ru.kremenia.market.api.ProductDto;
import ru.kremenia.market.core.converters.ProductConverter;
import ru.kremenia.market.core.entities.Product;
import ru.kremenia.market.core.exceptions.AppError;
import ru.kremenia.market.core.exceptions.ResourceNotFoundException;
import ru.kremenia.market.core.service.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @Operation(summary = "Запрос на получение отфильтрованного списка продуктов", responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = PageDto.class))
                    )
            }
    )

    @GetMapping
    public PageDto<ProductDto> findAllProducts(
            @RequestParam(required = false, name = "min_price") BigDecimal minPrice,
            @RequestParam(required = false, name = "max_price") BigDecimal maxPrice,
            @RequestParam(required = false, name = "title") String title,
            @RequestParam(defaultValue = "1", name = "p") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        Specification<Product> specification = productService.createSpecByFilters(minPrice, maxPrice, title);
        Page<ProductDto> myPage = productService.findAll(specification, page - 1).map(productConverter::entityToDto);
        PageDto<ProductDto> out = new PageDto<>();
        out.setItems(myPage.getContent());
        out.setPage(myPage.getNumber());
        out.setTotalPages(myPage.getTotalPages());
        return out;
    }

    @Operation(summary = "Запрос на получение продукта по id", responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    ),
                    @ApiResponse(
                            description = "Продукт не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )

    @GetMapping("/{id}")
    public ProductDto findProductById(@PathVariable @Parameter(description = "Идентификатор продукта", required = true) Long id) {
        Product p = productService.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Продукт не найден, id" + id));
        return productConverter.entityToDto(p);
    }

    @Operation(summary = "Запрос на создание нового продукта", responses = {
                    @ApiResponse(
                            description = "Продукт успешно создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = ProductDto.class))
                    )
            }
    )

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);
    }

    @DeleteMapping ("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
