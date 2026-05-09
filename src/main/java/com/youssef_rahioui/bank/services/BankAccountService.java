package com.youssef_rahioui.bank.services;

import com.youssef_rahioui.bank.dtos.AccountOperationDTO;
import com.youssef_rahioui.bank.dtos.BankAccountDTO;
import com.youssef_rahioui.bank.entities.BankAccount;

import java.util.List;

public interface BankAccountService {

    BankAccountDTO getAccount(Long accountId);
    List<BankAccountDTO> getCustomerAccounts(Long customerId);

    void debit(Long accountId, Double amount, String description);
    void credit(Long accountId, Double amount, String description);
    void transfer(Long fromAccountId, Long toAccountId, Double amount);

    // Historique
    List<AccountOperationDTO> getAccountHistory(Long accountId);

}
