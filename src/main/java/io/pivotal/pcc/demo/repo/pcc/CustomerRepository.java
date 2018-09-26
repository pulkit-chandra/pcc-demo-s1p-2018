package io.pivotal.pcc.demo.repo.pcc;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.pcc.demo.domain.Customer;

public interface CustomerRepository extends GemfireRepository<Customer, String> {
	
}
