package com.youssef_rahioui.bank.repositories;

import com.youssef_rahioui.bank.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findByCustomerId(Long customerId);
}