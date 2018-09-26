package io.pivotal.pcc.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientCache;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.tests.mock.annotation.EnableGemFireMockObjects;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.pivotal.pcc.demo.config.CloudCacheConfig;
import io.pivotal.pcc.demo.domain.Customer;
import io.pivotal.pcc.demo.repo.jpa.CustomerRepository;
import io.pivotal.pcc.demo.service.CustomerSearchService;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Import(DataSourceTestConfig.class)
public class PccDemoS1p2018ApplicationTests {
	

	@Autowired
	private CustomerSearchService customerSearchService;
	
	@Autowired
	private CustomerRepository jpaCustomerRepository;
	
	@Autowired
	private GemFireCache gemfireCache;
	
	private final String MOCK_USER_EMAIL = "test@xyz.io";
		
	
	@Test
	public void gemfireCacheIsAClientCache() {
		assertThat(this.gemfireCache).isInstanceOf(ClientCache.class);
	}
	
	@Test
	public void testCustomerSearchUsingEmail() {
		
		// Mock CustomerSearchService		
		List<Customer> customerMockResultList = new ArrayList<Customer>();
		customerMockResultList.add(getMockCustomerObject());
		Mockito.when(jpaCustomerRepository.findByEmail(MOCK_USER_EMAIL)).
		thenReturn(customerMockResultList);
		
		
		Mockito.when(customerSearchService.getCustomerByEmail(MOCK_USER_EMAIL)).
		thenReturn(getMockCustomerObject());
		Customer customer = customerSearchService.getCustomerByEmail(MOCK_USER_EMAIL);
		
		assertThat(customer.getEmail()).hasToString(MOCK_USER_EMAIL);
		
	}
	
	
	private Customer getMockCustomerObject() {
		Customer customer = new Customer();
	    customer.setId("customer1");
	    customer.setEmail("test@xyz.io");
	    return customer;
	}

	@Configuration
	@EnableGemFireMockObjects
	@Import(CloudCacheConfig.class)
	static class PccTestConfiguration { 
		
		@Bean(name="cacheManager")
		GemfireCacheManager cacheManager(GemFireCache gemfireCache) {
			GemfireCacheManager gemfireCacheManger = new GemfireCacheManager();
			gemfireCacheManger.setCache(gemfireCache);
			return gemfireCacheManger;
		}
		
	}
	
	@Configuration
	static class CustomerSearchMockTestConfiguration {
		
		@Bean
		@Primary
		public CustomerSearchService customerSearchService() {
			return Mockito.mock(CustomerSearchService.class);
		}
		
		@Bean
		@Primary
		public CustomerRepository jpaCustomerRepository() {
			return Mockito.mock(CustomerRepository.class);
		}
	}
	
	

}
