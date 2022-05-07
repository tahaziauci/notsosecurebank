package com.example.Broken.Bank.repository;

import com.example.Broken.Bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserByUsername(String username);
    User findUserByUsername(String username);
    boolean existsUserByUsernameAndPassword(String username, String password);
}
