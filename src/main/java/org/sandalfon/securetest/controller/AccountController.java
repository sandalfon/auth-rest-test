package org.sandalfon.securetest.controller;

import java.util.List;

import org.sandalfon.securetest.entity.Account;
import org.sandalfon.securetest.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.userdetails.User;

@RestController
public class AccountController {
	private @Autowired AccountRepository accountRepository;
	private @Autowired PasswordEncoder passwordEncoder;

	@Bean
	PasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	@PostMapping("/account")
	ResponseEntity<?> onPostAccount(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {

		final Account created = new Account();
		created.setUsername(username);
		created.setUsername(username);
		created.setPassword(passwordEncoder.encode(password));
		created.setRole("USER");
		accountRepository.save(created);

		return new ResponseEntity<>(accountRepository.findById(created.getId()), HttpStatus.OK);

	}

	@RequestMapping(method = RequestMethod.GET,
			value = "/account", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?> getAllAccounts(Pageable pageable,
			Authentication auth) {
		System.out.println("*****account******");
		Account account = accountRepository.findByUsername(auth.getName());
		if(account == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		System.out.println(account.toString());
		if ("ADMIN".equals(account.getRole())) {
			return new ResponseEntity<>(accountRepository.findAll(pageable), HttpStatus.OK);

		}
		return new ResponseEntity<>(accountRepository.findById(account.getId()), HttpStatus.OK);
	}



	@RequestMapping(method = RequestMethod.GET,
			value = "/account/{accountId}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<?>onGetAccount(@PathVariable Long accountId,
			Authentication auth) {
		System.out.println("*****account******");
		Account accountAuth = accountRepository.findByUsername(auth.getName());
		if(accountAuth == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		Account account = accountRepository.findById(accountId);
		if(account == null)
			return new ResponseEntity<>(null, HttpStatus.OK);
		System.out.println(accountAuth.toString());
		if ("ADMIN".equals(accountAuth.getRole())) {
			return new ResponseEntity<>(account, HttpStatus.OK);

		}
		if(account.getId() == accountAuth.getId())
			return new ResponseEntity<>(account, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}





}
