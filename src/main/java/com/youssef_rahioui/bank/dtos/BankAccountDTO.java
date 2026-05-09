package com.youssef_rahioui.bank.dtos;

import com.youssef_rahioui.bank.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Long id;
    private Double balance;
    private LocalDateTime createdAt;
    private String status;
    private Long customerId;
}