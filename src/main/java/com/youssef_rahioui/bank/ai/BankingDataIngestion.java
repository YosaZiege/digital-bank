package com.youssef_rahioui.bank.ai;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Order(2)
public class BankingDataIngestion implements CommandLineRunner {

    private final RagService ragService;

    @Override
    public void run(String... args) {
        ragService.ingest(List.of(
                "To open a current account you must be at least 18 years old and provide a valid ID, proof of address, and an initial deposit of at least 100 MAD.",
                "Savings accounts earn an annual interest rate of 3.5%. Interest is calculated monthly and credited at the end of each quarter.",
                "Transfers between accounts within the bank are instant and free. External transfers to other banks take 1-3 business days and may incur a fee of 5 MAD.",
                "Card payments are capped at 10,000 MAD per day. ATM withdrawals are limited to 2,000 MAD per transaction and 5,000 MAD per day.",
                "To report a lost or stolen card, call our 24/7 hotline at 0800-BANK or block it instantly via the mobile app.",
                "Overdraft protection is available on current accounts up to a limit set by the bank based on your credit profile. Interest on overdraft is 12% per annum.",
                "Loan applications require proof of income, a credit score above 600, and a debt-to-income ratio below 40%.",
                "The bank's working hours are Monday to Friday from 8:30am to 4:30pm. ATMs are available 24/7.",
                "Account statements are generated monthly and can be downloaded from the online banking portal or requested at any branch.",
                "Dormant accounts (no activity for 12 months) are charged a maintenance fee of 20 MAD per month. Accounts inactive for 5 years are transferred to the national treasury."
        ));
    }
}
