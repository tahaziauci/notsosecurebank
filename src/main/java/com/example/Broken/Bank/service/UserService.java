package com.example.Broken.Bank.service;

import com.example.Broken.Bank.Response.MoneyRequest;
import com.example.Broken.Bank.entity.User;
import com.example.Broken.Bank.model.UserModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;

public interface UserService {

    ResponseEntity saveNewUser(UserModel userModel);
    ResponseEntity checkUser(User user, HttpSession session);
    ResponseEntity viewUserBalance(HttpSession session);
    ResponseEntity withdraw(HttpSession session, MoneyRequest moneyRequest);
}
