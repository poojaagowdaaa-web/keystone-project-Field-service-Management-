package jsp.springboot.keystone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import jsp.springboot.keystone.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}