package com.example.Broken.Bank.service;

import com.example.Broken.Bank.Response.MoneyRequest;
import com.example.Broken.Bank.entity.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService {

    ResponseEntity saveNewUser(User user);
    ResponseEntity checkUser(User user, HttpSession session);
    ResponseEntity viewUserBalance(HttpSession session);
    ResponseEntity withdraw(HttpSession session, MoneyRequest moneyRequest);
    ResponseEntity deposit(HttpSession session, MoneyRequest moneyRequest);

    ResponseEntity getAllUsers();
}
