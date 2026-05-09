package com.youssef_rahioui.bank.repositories;

import com.youssef_rahioui.bank.entities.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingAccountRepository extends JpaRepository<SavingAccount, Long> {
}