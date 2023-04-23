package com.petkpetk.service.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petkpetk.service.domain.user.entity.ProfileImage;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
