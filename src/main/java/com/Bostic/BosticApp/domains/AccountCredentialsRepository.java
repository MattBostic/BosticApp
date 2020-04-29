package com.Bostic.BosticApp.domains;

import org.springframework.data.repository.CrudRepository;

public interface AccountCredentialsRepository extends CrudRepository<AccountCredentials, Long> {
    AccountCredentials findByUsername(String username);
}
