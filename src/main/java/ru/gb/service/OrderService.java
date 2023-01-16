package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import ru.gb.model.Customer;
import ru.gb.model.Order;
import ru.gb.repository.CustomerRepository;
import ru.gb.repository.OrderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final OrderNumberService orderNumberService;

    public Order create(Long customerId) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow();

        var order = new Order();
        order.setCustomerId(customer.getId());
        order.setNumber(orderNumberService.next());

        return orderRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

}
