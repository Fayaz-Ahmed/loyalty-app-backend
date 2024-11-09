package com.univiser.loyaltysystem.service;

import com.univiser.loyaltysystem.dto.AddPointsDTO;
import com.univiser.loyaltysystem.dto.CustomerCreateDTO;
import com.univiser.loyaltysystem.dto.CustomerPointsDTO;
import com.univiser.loyaltysystem.dto.RedeemPointsDTO;
import com.univiser.loyaltysystem.model.Customer;
import com.univiser.loyaltysystem.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateDTO customerCreateDTO) {
        if (customerRepository.findByEmail(customerCreateDTO.getEmail()) != null) {
            logger.error("Customer creation failed: Email {} is already in use.", customerCreateDTO.getEmail());
            throw new IllegalArgumentException("Email is already in use");
        }

        Customer customer = new Customer(customerCreateDTO.getName(), customerCreateDTO.getEmail());
        Customer savedCustomer = customerRepository.save(customer);
        logger.info("Customer created successfully: {}", savedCustomer);
        return savedCustomer;
    }

    public Customer addPoints(AddPointsDTO addPointsDTO) {
        Customer customer = customerRepository.findByEmail(addPointsDTO.getEmail());
        if (customer == null) {
            logger.error("Add points failed: Customer with email {} not found.", addPointsDTO.getEmail());
            throw new IllegalArgumentException("Customer not found");
        }

        customer.setPoints(customer.getPoints() + addPointsDTO.getPoints());
        Customer updatedCustomer = customerRepository.save(customer);
        logger.info("Points added successfully. Customer: {}, Added Points: {}", updatedCustomer, addPointsDTO.getPoints());
        return updatedCustomer;
    }

    public CustomerPointsDTO viewPoints(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            logger.error("View points failed: Customer with email {} not found.", email);
            throw new IllegalArgumentException("Customer not found");
        }

        logger.info("Viewed points successfully. Customer: {}, Points: {}", customer, customer.getPoints());
        return new CustomerPointsDTO(email, customer.getPoints());
    }

    @Transactional
    public Customer redeemPoints(RedeemPointsDTO redeemPointsDTO) {
        Customer customer = customerRepository.findByEmail(redeemPointsDTO.getEmail());
        if (customer == null) {
            logger.error("Redeem points failed: Customer with email {} not found.", redeemPointsDTO.getEmail());
            throw new IllegalArgumentException("Customer not found");
        }

        if (customer.getPoints() < redeemPointsDTO.getPoints()) {
            logger.error("Redeem points failed: Insufficient points for customer {}", customer);
            throw new IllegalArgumentException("Insufficient points");
        }

        customer.setPoints(customer.getPoints() - redeemPointsDTO.getPoints());
        logger.info("Points redeemed successfully. Customer: {}, Redeemed Points: {}", customer, redeemPointsDTO.getPoints());
        return customer;
    }
}