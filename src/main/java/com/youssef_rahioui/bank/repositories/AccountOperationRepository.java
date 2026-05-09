package com.youssef_rahioui.bank.repositories;

import com.youssef_rahioui.bank.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(Long bankAccountId);
}