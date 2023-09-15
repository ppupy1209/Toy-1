package toyproject.toyshop.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import toyproject.toyshop.domain.Address;
import toyproject.toyshop.domain.Order;
import toyproject.toyshop.domain.OrderStatus;
import toyproject.toyshop.repository.OrderRepository;
import toyproject.toyshop.repository.OrderSearch;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<SimpleOrderDto> ordersV1() {
        List<Order> orders = orderRepository.findAllWithMemberDeliver();
       return orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }

}
