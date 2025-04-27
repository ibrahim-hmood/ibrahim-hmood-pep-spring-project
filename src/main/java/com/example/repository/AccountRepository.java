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
    /*
     * findByUsername: finds account by username
     * @param username: username to be searched for
     * @returns Account associated with username
     */
    public Account findByUsername(String username);

    /*
     * findByUsernameAndPassword: finds account by username and password
     * @param username: username to look for
     * @param password: possible password for username
     * @returns Account associated with username and password
     */
    public Account findByUsernameAndPassword(String username, String password);
}
