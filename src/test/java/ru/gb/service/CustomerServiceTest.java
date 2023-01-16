package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.SpringBootTestBase;
import ru.gb.model.Customer;
import ru.gb.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest extends SpringBootTestBase {

    @Autowired
    CustomerService service;

    @Autowired
    CustomerRepository repository;

    @Test
    void create() {
        Customer createdCustomer = service.create("TestCustomer");
        Assertions.assertNotNull(createdCustomer);
        Assertions.assertNotNull(createdCustomer.getId());
        Assertions.assertEquals("TestCustomer", createdCustomer.getName());

        Customer savedCustomer = repository.findById(createdCustomer.getId()).orElse(null);
        Assertions.assertEquals("TestCustomer", savedCustomer.getName());
    }

}