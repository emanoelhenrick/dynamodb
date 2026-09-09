package br.com.dynamodb.controller;

import br.com.dynamodb.dto.CustomerDTO;
import br.com.dynamodb.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customerDTO));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> listCustomers(@RequestParam(required = false) String companyName) {
        if (companyName == null || companyName.isBlank()) {
            return ResponseEntity.ok(customerService.findAllCustomers());
        }
        return ResponseEntity.ok(customerService.findByCompanyName(companyName));
    }

    @GetMapping("/query")
    public ResponseEntity<CustomerDTO> findCompanyNameByQuery(@RequestParam String companyName) {
        return ResponseEntity.ok(customerService.findCompanyNameByQuery(companyName));
    }

    @PatchMapping
    public ResponseEntity<CustomerDTO> updateCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(customerDTO));
    }

    @PatchMapping("/{companyDocumentNumber}")
    public ResponseEntity<CustomerDTO> disableCustomer(@PathVariable String companyDocumentNumber) {
        return ResponseEntity.ok(customerService.disableCustomer(companyDocumentNumber));
    }

}