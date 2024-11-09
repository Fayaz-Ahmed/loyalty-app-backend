package com.univiser.loyaltysystem.repository;

import com.univiser.loyaltysystem.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer,Long> {
    Customer findByEmail(String email);
}
