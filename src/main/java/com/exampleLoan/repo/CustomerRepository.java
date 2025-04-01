package com.exampleLoan.repo;

import com.exampleLoan.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {


    public Customer findByEmail(String email);
}
