package com.petkpetk.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.admin.entity.AdminAccount;

public interface AdminAccountRepository extends JpaRepository<AdminAccount, Long> {

	Optional<AdminAccount> findByEmail(String email);

	boolean existsByEmail(String email);

}
