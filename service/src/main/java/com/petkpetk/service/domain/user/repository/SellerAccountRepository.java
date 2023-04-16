package com.petkpetk.service.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.user.entity.SellerAccount;

public interface SellerAccountRepository extends JpaRepository<SellerAccount, Long> {

	Optional<SellerAccount> findByEmail(String email);
}

