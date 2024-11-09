package com.univiser.loyaltysystem.controller;

import com.univiser.loyaltysystem.dto.AddPointsDTO;
import com.univiser.loyaltysystem.dto.CustomerCreateDTO;
import com.univiser.loyaltysystem.dto.CustomerPointsDTO;
import com.univiser.loyaltysystem.dto.RedeemPointsDTO;
import com.univiser.loyaltysystem.model.Customer;
import com.univiser.loyaltysystem.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerCreateDTO customerCreateDTO) {
        return ResponseEntity.ok(customerService.createCustomer(customerCreateDTO));
    }

    @PostMapping("/add-points")
    public ResponseEntity<Customer> addPoints(@Valid @RequestBody AddPointsDTO addPointsDTO) {
        return ResponseEntity.ok(customerService.addPoints(addPointsDTO));
    }

    @GetMapping("/view-points")
    public ResponseEntity<CustomerPointsDTO> viewPoints(@RequestParam String email) {
        return ResponseEntity.ok(customerService.viewPoints(email));
    }

    @PostMapping("/redeem-points")
    public ResponseEntity<Customer> redeemPoints(@Valid @RequestBody RedeemPointsDTO redeemPointsDTO) {
        return ResponseEntity.ok(customerService.redeemPoints(redeemPointsDTO));
    }
}