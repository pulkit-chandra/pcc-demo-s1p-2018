package io.pivotal.pcc.demo.repo.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.pivotal.pcc.demo.domain.Customer;

@Repository("JpaCustomerRepository")
public interface CustomerRepository extends JpaRepository<Customer, String> {

	List<Customer> findByEmail(final String email);
	
}
