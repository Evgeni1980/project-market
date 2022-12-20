package ru.kremenia.market.api;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private Long id;
    private  String username;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> items, BigDecimal totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
