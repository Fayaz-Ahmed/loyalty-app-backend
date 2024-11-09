package com.univiser.loyaltysystem.service;

import com.univiser.loyaltysystem.dto.AddPointsDTO;
import com.univiser.loyaltysystem.dto.CustomerCreateDTO;
import com.univiser.loyaltysystem.dto.CustomerPointsDTO;
import com.univiser.loyaltysystem.dto.RedeemPointsDTO;
import com.univiser.loyaltysystem.model.Customer;
import com.univiser.loyaltysystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCustomer() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");

        Customer customer = new Customer("John Doe", "john.doe@example.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer createdCustomer = customerService.createCustomer(dto);

        assertNotNull(createdCustomer);
        assertEquals("John Doe", createdCustomer.getName());
        assertEquals("john.doe@example.com", createdCustomer.getEmail());
    }

    @Test
    public void testAddPoints() {
        String email = "john.doe@example.com";
        AddPointsDTO dto = new AddPointsDTO();
        dto.setEmail(email);
        dto.setPoints(100);

        Customer customer = new Customer("John Doe", email);
        customer.setPoints(0);

        when(customerRepository.findByEmail(email)).thenReturn(customer);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer updatedCustomer = customerService.addPoints(dto);

        assertEquals(100, updatedCustomer.getPoints());
    }

    @Test
    public void testRedeemPoints() {
        String email = "john.doe@example.com";
        RedeemPointsDTO dto = new RedeemPointsDTO();
        dto.setEmail(email);
        dto.setPoints(50);

        Customer customer = new Customer("John Doe", email);
        customer.setPoints(100);

        when(customerRepository.findByEmail(email)).thenReturn(customer);

        Customer updatedCustomer = customerService.redeemPoints(dto);

        assertEquals(50, updatedCustomer.getPoints());
    }

    @Test
    public void testViewPoints() {
        String email = "john.doe@example.com";
        Customer customer = new Customer("John Doe", email);
        customer.setPoints(100);

        // Mock the repository to return the customer when findByEmail is called
        when(customerRepository.findByEmail(email)).thenReturn(customer);

        // Call the service method and retrieve CustomerPointsDTO
        CustomerPointsDTO pointsDTO = customerService.viewPoints(email);

        // Assert the points in the returned DTO
        assertEquals(100, pointsDTO.getPoints());
    }

}