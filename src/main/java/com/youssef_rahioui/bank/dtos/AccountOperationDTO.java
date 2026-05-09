package com.youssef_rahioui.bank.dtos;

import com.youssef_rahioui.bank.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOperationDTO {
    private Long id;
    private Double amount;
    private LocalDateTime operationDate;
    private String type;
    private Long bankAccountId;
}