package ru.kremenia.market.core.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.kremenia.market.api.CartDto;
import ru.kremenia.market.core.entities.Order;
import ru.kremenia.market.core.entities.OrderItem;
import ru.kremenia.market.core.integration.CartServiceIntegration;
import ru.kremenia.market.core.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@EnableTransactionManagement(proxyTargetClass = true)
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final CartServiceIntegration cartServiceIntegration;

//    @Transactional
//    public Order createOrder(String username) {
//        CartDto cartDto = cartServiceIntegration.getCart(username);
//        Order order = new Order();
//        order.setUsername(username);
//        order.setTotalPrice(cartDto.getTotalPrice());
//        order.setItems(cartDto.getItems().stream().map(
//                cartItemDto -> new OrderItem(
//                        productService.findById(cartItemDto.getProductId()).get(),
//                        order,
//                        cartItemDto.getQuantity(),
//                        cartItemDto.getPricePerProduct(),
//                        cartItemDto.getPrice()
//                )
//        ).collect(Collectors.toList()));
//        orderRepository.save(order);
//        cartServiceIntegration.clear(username);
//        return order;
//    }

    @Transactional
    public Order createOrder(String username) {
        CartDto cart = cartServiceIntegration.getCart(username);
        Order order = new Order();
        List<OrderItem> orderItem = cart.getItems().stream().map(cartItem -> new OrderItem(
                productService.findById(cartItem.getProductId()).get(),
                order,
                cartItem.getQuantity(),
                cartItem.getPrice(),
                cartItem.getPricePerProduct())).collect(Collectors.toList());
        order.setUsername(username);
        order.setItems(orderItem);
        order.setTotalPrice(countPrice(orderItem));
        orderRepository.save(order);
        return order;
    }

    private BigDecimal countPrice(List<OrderItem> orderItems){
        BigDecimal total = BigDecimal.ZERO;
        for(OrderItem item: orderItems){
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

//    public List<Order> findByUsername(String username) {
//        return orderRepository.findByUsername(username);
//    }

    public List<Order> getOrders(String username) {
        return orderRepository.findByUsername(username);
    }

}
