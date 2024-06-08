package com.example.yoonnsshop.domain.admins.repository;

import com.example.yoonnsshop.domain.admins.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByEmail(String email);
}
