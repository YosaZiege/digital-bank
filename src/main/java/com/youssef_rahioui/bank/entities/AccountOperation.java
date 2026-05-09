package com.youssef_rahioui.bank.entities;


import com.youssef_rahioui.bank.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "operation")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AccountOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDateTime operationDate;

    @Enumerated(EnumType.STRING)
    private OperationType type;


    @ManyToOne
    @JoinColumn(name = "bankAccount_id" , nullable = false)
    private BankAccount bankAccount;
    @PrePersist
    protected void onCreate() {
        if (this.operationDate == null) {
            this.operationDate = LocalDateTime.now();
        }
    }
}
