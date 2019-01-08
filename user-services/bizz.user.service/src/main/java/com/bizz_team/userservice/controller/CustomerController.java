/*package com.bizz_team.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizz_team.userservice.model.Customer;
import com.bizz_team.userservice.repository.CustomerRepo;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	@Autowired
	private CustomerRepo customerRepo;
 
	@PostMapping(value = "/save")
	public List<Customer> postCustomer(@RequestBody Customer customer) {
		customerRepo.save(customer);
		customerRepo.findAll();
		
		String fullName = customer.getFirstname() + " " + customer.getLastname();
		System.out.println(fullName);
		
		//return "Hello " + fullName + "!" + " (Message from SpringBoot Server)";
		return customerRepo.findAll();
	}

}
*/