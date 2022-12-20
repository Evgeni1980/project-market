package ru.kremenia.market.core.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kremenia.market.api.OrderDto;
import ru.kremenia.market.core.converters.OrderConverter;
import ru.kremenia.market.core.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username) {
        orderService.createOrder(username);
    }

    @GetMapping
    public List<OrderDto> getOrders(@RequestHeader String username) {
        return orderService.getOrders(username)
                .stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }

}
