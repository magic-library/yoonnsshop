package com.example.yoonnsshop.domain.admins.repository;

import com.example.yoonnsshop.domain.admins.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>{
}
