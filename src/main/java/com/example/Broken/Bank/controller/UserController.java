package com.example.Broken.Bank.controller;

import com.example.Broken.Bank.Response.MoneyRequest;
import com.example.Broken.Bank.entity.User;
import com.example.Broken.Bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user) {
        return userService.saveNewUser(user);
    }


    @PostMapping("/signin")
    public ResponseEntity signin(@Valid @RequestBody User user, HttpSession session) {
        return userService.checkUser(user, session);
    }

    @GetMapping("/view")
    public ResponseEntity viewAccount(HttpSession session) {
        return userService.viewUserBalance(session);
    }
    // withdraw, deposit

    @PostMapping("/withdraw")
    public ResponseEntity withdraw(HttpSession session, @Valid @RequestBody MoneyRequest moneyRequest) {
        return userService.withdraw(session, moneyRequest);
    }
}
