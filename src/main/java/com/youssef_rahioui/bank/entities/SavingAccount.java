package com.youssef_rahioui.bank.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SAVING_ACCOUNT")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavingAccount extends BankAccount {

    private Double interestRate;
}