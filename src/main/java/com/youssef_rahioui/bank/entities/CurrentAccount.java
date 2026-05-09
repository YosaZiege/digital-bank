package com.youssef_rahioui.bank.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("CURRENT_ACCOUNT")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class CurrentAccount extends BankAccount{
    private Double overDraft;
}
