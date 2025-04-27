package com.example.service;

import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService 
{
    @Autowired
    private AccountRepository repository;

    /*
     * findAccountByID: searches for account using given ID
     * @param id: potential ID of account
     * @returns Account if ID is found
     */
    public Optional<Account> findAccountByID(int id)
    {
        //Look for account using ID
        return repository.findById(id);
    }

    /*
     * findAccountByUserName: searches for account by username
     * @param username: username to look for
     * @returns Account if one is found
     */
    public Account findAccountByUserName(String username)
    {
        //Look for username
        return repository.findByUsername(username);
    }

    /*
     * addAccount: adds account to account table
     * @param account: account to be added
     * @returns new Account
     */
    public Account addAccount(Account account)
    {
        //Save the account
        return repository.save(account);
    }

    /*
     * findAccountByUsernamePassword: looks for account by username and password
     * @param username: username to look for
     * @param password: possible password associated with username
     * @returns Account if one is found
     */
    public Account findAccountByUsernamePassword(Account account)
    {
        //Look for account using username and password
        return repository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }
}
