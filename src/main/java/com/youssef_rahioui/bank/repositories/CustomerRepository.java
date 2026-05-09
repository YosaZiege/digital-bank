package com.youssef_rahioui.bank.repositories;

import com.youssef_rahioui.bank.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
