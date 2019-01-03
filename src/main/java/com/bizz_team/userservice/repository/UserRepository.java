package com.bizz_team.userservice.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bizz_team.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByMobile(String mobile);
   // List<User> findByUserName(String name);
}