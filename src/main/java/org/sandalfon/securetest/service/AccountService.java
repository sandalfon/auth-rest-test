package org.sandalfon.securetest.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
	 @Secured ({"ROLE_ADMIN"})
	 ResponseEntity<?>onPostAccount(String username, String password, String role);
	 @Secured ({"ROLE_ADMIN"})
	 ResponseEntity<?> getAllAccounts();
     @Secured ({"ROLE_ADMIN", "ROLE_USER"})
	 ResponseEntity<?>onGetAccount(Long accountId, Authentication auth);
	
}
