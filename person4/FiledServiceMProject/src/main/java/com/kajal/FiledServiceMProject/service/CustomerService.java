package com.kajal.FiledServiceMProject.service;

import com.kajal.FiledServiceMProject.Entity.Customer;
import com.kajal.FiledServiceMProject.dto.CustomerRequest;
import com.kajal.FiledServiceMProject.dto.CustomerResponse;
import com.kajal.FiledServiceMProject.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerResponse create(CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setContactEmail(request.getContactEmail());
        Customer saved = customerRepository.save(customer);
        return toResponse(saved);
    }

    public Page<CustomerResponse> search(String name, Pageable pageable) {
        String query = name == null ? "" : name;
        return customerRepository.findByNameContainingIgnoreCase(query, pageable)
                .map(this::toResponse);
    }

    public CustomerResponse getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found: " + id));
        return toResponse(customer);
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(c.getId(), c.getName(), c.getContactEmail(), c.getCreatedAt());
    }
}