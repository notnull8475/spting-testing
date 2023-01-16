package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
