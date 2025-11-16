package com.example.CustomerService;

import com.example.CustomerService.entity.Customer;
import com.example.CustomerService.repository.CustomerRepository;
import static com.example.common.constrants.CustomerConstants.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class CustomerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner ensureDefaultCustomer(CustomerRepository repository) {
		return args -> {

			repository.findByPhone(DEFAULT_CUSTOMER_PHONE).orElseGet(() -> {
				Customer guest = Customer.builder()
                        .id(DEFAULT_CUSTOMER_ID)
					.name(DEFAULT_CUSTOMER_NAME)
					.phone(DEFAULT_CUSTOMER_PHONE)
					.email(DEFAULT_CUSTOMER_EMAIL)
					.address(DEFAULT_CUSTOMER_ADDRESS)
					.gender(DEFAULT_CUSTOMER_GENDER) // default unknown/guest
					.build();
				System.out.println("Creating default guest customer with phone: " + DEFAULT_CUSTOMER_PHONE);
				return repository.save(guest);
			});
		};
	}

}
