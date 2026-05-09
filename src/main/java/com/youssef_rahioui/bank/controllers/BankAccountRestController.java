package com.youssef_rahioui.bank.controllers;


import com.youssef_rahioui.bank.dtos.AccountOperationDTO;
import com.youssef_rahioui.bank.dtos.BankAccountDTO;
import com.youssef_rahioui.bank.services.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class BankAccountRestController {
    private final BankAccountService bankAccountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountDTO> getAccount(@PathVariable Long accountId) {
        BankAccountDTO account = bankAccountService.getAccount(accountId);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<BankAccountDTO>> getCustomerAccounts(@PathVariable Long customerId) {
        List<BankAccountDTO> accounts = bankAccountService.getCustomerAccounts(customerId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<List<AccountOperationDTO>> getAccountHistory(@PathVariable Long accountId) {
        List<AccountOperationDTO> history = bankAccountService.getAccountHistory(accountId);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/{accountId}/debit")
    public ResponseEntity<Void> debit(
            @PathVariable Long accountId,
            @RequestParam Double amount,
            @RequestParam(required = false) String description) {
        bankAccountService.debit(accountId, amount, description);
        return ResponseEntity.ok().build();  // ✓ Returns HTTP 200
    }

    @PostMapping("/{accountId}/credit")
    public ResponseEntity<Void> credit(
            @PathVariable Long accountId,
            @RequestParam Double amount,
            @RequestParam(required = false) String description) {
        bankAccountService.credit(accountId, amount, description);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam Double amount) {
        bankAccountService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok().build();
    }


}
