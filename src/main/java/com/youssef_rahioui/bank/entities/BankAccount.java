package com.youssef_rahioui.bank.entities;

import com.youssef_rahioui.bank.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@DiscriminatorColumn(name="account_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@AllArgsConstructor

 public abstract class BankAccount  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double balance;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne      // Plusieurs comptes pour un client
    @JoinColumn(name = "customer_id" , nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = AccountStatus.ACTIVE;
    }
}
