package ru.gb.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import ru.gb.SpringBootTestBase;
import ru.gb.model.Customer;
import ru.gb.model.Order;
import ru.gb.repository.CustomerRepository;
import ru.gb.repository.OrderRepository;

import java.util.NoSuchElementException;

// ru.gb.service
// ru.gb
// ru.gb.service
// ru.gb.repository
//@SpringBootTest(classes = OrderServiceTest.OrderNumberServiceTestConfiguration.class)
class OrderServiceTest extends SpringBootTestBase {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @MockBean
    OrderNumberService orderNumberService;

//    @TestConfiguration
//    static class OrderNumberServiceTestConfiguration {
//        @Bean
//        @Primary
//        public OrderNumberService testOrderNumberService() {
//            return new OrderNumberService() {
//                @Override
//                public String next() {
//                    return "0";
//                }
//            };
//        }
//    }

    // [[os] -> [jre] -> [jar]] = docker-container

    @Test
    void testCreateOrder() {
        var customer = new Customer();
        customer.setName("TestCustomer");
        customer = customerRepository.save(customer);

        Mockito.when(orderNumberService.next()).thenReturn("TestNumber");

        Order createdOrder = orderService.create(customer.getId());
        Assertions.assertNotNull(createdOrder);
        Assertions.assertNotNull(createdOrder.getId());
        Assertions.assertEquals(customer.getId(), createdOrder.getCustomerId());
        Assertions.assertEquals("TestNumber", createdOrder.getNumber());

        Order savedOrder = orderRepository.findById(createdOrder.getId()).orElse(null);
        org.assertj.core.api.Assertions.assertThat(createdOrder)
                .returns(savedOrder.getId(), Order::getId)
                .returns(savedOrder.getCustomerId(), Order::getCustomerId)
                .returns(savedOrder.getNumber(), Order::getNumber);
//        Assertions.assertEquals(createdOrder.getId(), savedOrder.getId());
//        Assertions.assertEquals(createdOrder.getCustomerId(), savedOrder.getCustomerId());
//        Assertions.assertEquals(createdOrder.getNumber(), savedOrder.getNumber());
    }

    @Test
    void testCreateOrderCustomerNotFound() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> orderService.create(-1L))
                .isInstanceOf(NoSuchElementException.class);

        Mockito.verifyNoInteractions(orderNumberService);
//        Mockito.verify(orderNumberService, Mockito.times(0)).next();
    }

}