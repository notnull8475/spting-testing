package ru.gb.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Customer;
import ru.gb.model.Order;
import ru.gb.repository.OrderRepository;
import ru.gb.service.OrderService;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/new")
    public ResponseEntity<Order> create(@RequestParam Long customerId) {
        return ResponseEntity.ok(service.create(customerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
