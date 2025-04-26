package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>
{
    public Account findByUsername(String username);

    public Account findByUsernameAndPassword(String username, String password);
}
