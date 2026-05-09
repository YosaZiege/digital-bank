package com.youssef_rahioui.bank.services;


import com.youssef_rahioui.bank.dtos.AccountOperationDTO;
import com.youssef_rahioui.bank.dtos.BankAccountDTO;
import com.youssef_rahioui.bank.entities.AccountOperation;
import com.youssef_rahioui.bank.entities.BankAccount;
import com.youssef_rahioui.bank.enums.OperationType;
import com.youssef_rahioui.bank.repositories.AccountOperationRepository;
import com.youssef_rahioui.bank.repositories.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final AccountOperationRepository accountOperationRepository;

    @Override
    public BankAccountDTO getAccount(Long accoundId){
        BankAccount account = bankAccountRepository.findById(accoundId).orElseThrow(() -> new RuntimeException("Account not found"));
        return mapToDTO(account);
    }
    @Override
    public List<BankAccountDTO> getCustomerAccounts(Long customerId) {
        List<BankAccount> accounts = bankAccountRepository.findByCustomerId(customerId);
        return accounts.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void debit(Long accountId, Double amount, String description) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        bankAccountRepository.save(account);

        recordOperation(account, amount, OperationType.DEBIT);
    }

    @Override
    public void credit(Long accountId, Double amount, String description) {
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance() + amount);
        bankAccountRepository.save(account);

        recordOperation(account, amount, OperationType.CREDIT);
    }
    @Override
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        BankAccount fromAccount = bankAccountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        BankAccount toAccount = bankAccountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);

        toAccount.setBalance(toAccount.getBalance() + amount);

        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        recordOperation(fromAccount, amount, OperationType.DEBIT);
        recordOperation(toAccount, amount, OperationType.CREDIT);
    }

    @Override
    public List<AccountOperationDTO> getAccountHistory(Long accountId) {
        List<AccountOperation> operations = accountOperationRepository.findByBankAccountId(accountId);
        return operations.stream()
                .map(this::mapOperationToDTO)
                .collect(Collectors.toList());
    }

    private void recordOperation(BankAccount account, Double amount, OperationType type) {
        AccountOperation operation = new AccountOperation();
        operation.setAmount(amount);
        operation.setOperationDate(LocalDateTime.now());
        operation.setType(type);
        operation.setBankAccount(account);
        accountOperationRepository.save(operation);
    }

    private BankAccountDTO mapToDTO(BankAccount account) {
        BankAccountDTO dto = new BankAccountDTO();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setCreatedAt(account.getCreatedAt());
        dto.setStatus(account.getStatus().toString());
        dto.setCustomerId(account.getCustomer().getId());
        return dto;
    }

    private AccountOperationDTO mapOperationToDTO(AccountOperation operation) {
        AccountOperationDTO dto = new AccountOperationDTO();
        dto.setId(operation.getId());
        dto.setAmount(operation.getAmount());
        dto.setOperationDate(operation.getOperationDate());
        dto.setType(operation.getType().toString());
        dto.setBankAccountId(operation.getBankAccount().getId());
        return dto;
    }

}
