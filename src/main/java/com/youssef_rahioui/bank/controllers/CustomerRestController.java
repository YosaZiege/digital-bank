package com.youssef_rahioui.bank.controllers;


import com.youssef_rahioui.bank.dtos.CustomerDTO;
import com.youssef_rahioui.bank.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Long customerId) {
        CustomerDTO customer = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.saveCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long customerId,
            @RequestBody CustomerDTO customerDTO) {
        CustomerDTO updatedCustomer = customerService.updateCustomer(customerId, customerDTO);
        return ResponseEntity.ok(updatedCustomer);
    }
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build(); // http 204
    }
}
