package org.sandalfon.securetest.repository;


import org.sandalfon.securetest.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "/netheos/account")
public interface AccountRepository extends JpaRepository<Account, Long>{

	Account findById(Long accountid);
	Account findByUsername(String username);

}
