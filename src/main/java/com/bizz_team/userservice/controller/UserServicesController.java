package com.bizz_team.userservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bizz_team.userservice.model.User;
import com.bizz_team.userservice.model.Users;
import com.bizz_team.userservice.repository.UserRepository;

@RestController
@RequestMapping("/rest/user")
public class UserServicesController {
	private UserRepository userRepository;

	public UserServicesController(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@GetMapping("/{mobile}")
	public List<User> getUser(@PathVariable("mobile") final String mobile) {

		return getNameBymobile(mobile);
	}

	@GetMapping("/alluser")
	public List<User> getAllQuotes() {

		return getAllUsers();
	} 	

	@PostMapping("/add")
	public List<User> add(@RequestBody final User user) {
		System.out.println("adduser called:"+user);
		userRepository.save(user);
		return getNameBymobile(user.getMobile());
	}

	@PostMapping("/login")
	public List<User> login(@RequestBody final User user) {
		String mob= user.getMobile();
		String pwd= user.getPassword();
		List<User> user1= userRepository.findByMobile(mob);
		user1.forEach(n-> System.out.println(n.getAcc_no()+"/n"+n.getName()+"/n"+n.getPassword()));
		user1.forEach(n-> { if (n.getMobile().equals(mob)) System.out.println("Login Successfull"); });
		/*if(user1.forEach(n-> n.getMobile().equals(mob))){
			System.out.println("Login Successfully");
			break;
		}*/
		
		return userRepository.findByMobile(user.getMobile());
	}
	/*@PostMapping("/delete/{username}")
	public List<String> delete(@PathVariable("username") final String username) {
		List<Quote> quotes = quotesRepository.findByUserName(username);
		quotesRepository.delete(quotes);
		return getQuotesByUserName(username);
	}*/

	private List<User> getAllUsers() {
		return userRepository.findAll();
	}

	private List<User> getNameBymobile(@PathVariable("mobile") String username) {
		//return userRepository.findByMobile(username).stream().map(User::getName).collect(Collectors.toList());
		return userRepository.findByMobile(username);
	}

}