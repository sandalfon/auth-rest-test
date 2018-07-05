package org.sandalfon.securetest.controller;

import java.util.Arrays;

import org.sandalfon.securetest.entity.Account;
import org.sandalfon.securetest.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailsService implements UserDetailsService {
	@Autowired
    private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

			Account activeAccount = accountRepository.findByUsername(username);
			GrantedAuthority authority = new SimpleGrantedAuthority(activeAccount.getRole());
			UserDetails userDetails = (UserDetails)new User(activeAccount.getUsername(),
					activeAccount.getPassword(), Arrays.asList(authority));
			System.out.println(userDetails);
			return userDetails;
	}

}
