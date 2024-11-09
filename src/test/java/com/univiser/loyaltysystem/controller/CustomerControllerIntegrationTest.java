package com.univiser.loyaltysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.univiser.loyaltysystem.dto.CustomerCreateDTO;
import com.univiser.loyaltysystem.model.Customer;
import com.univiser.loyaltysystem.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateCustomer() throws Exception {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");

        mockMvc.perform(post("/api/customers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testAddPoints() throws Exception {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customer.setPoints(0);
        customerRepository.save(customer);

        String addPointsJson = "{ \"email\": \"john.doe@example.com\", \"points\": 100 }";

        mockMvc.perform(post("/api/customers/add-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(addPointsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(100));
    }

    @Test
    public void testRedeemPoints() throws Exception {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customer.setPoints(100);
        customerRepository.save(customer);

        String redeemPointsJson = "{ \"email\": \"john.doe@example.com\", \"points\": 50 }";

        mockMvc.perform(post("/api/customers/redeem-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(redeemPointsJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(50));
    }

    @Test
    public void testViewPoints() throws Exception {
        Customer customer = new Customer("John Doe", "john.doe@example.com");
        customer.setPoints(100);
        customerRepository.save(customer);

        mockMvc.perform(get("/api/customers/view-points")
                        .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(100));
    }
}