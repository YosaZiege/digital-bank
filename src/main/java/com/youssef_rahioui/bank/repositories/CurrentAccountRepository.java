package com.youssef_rahioui.bank.repositories;

import com.youssef_rahioui.bank.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {
}