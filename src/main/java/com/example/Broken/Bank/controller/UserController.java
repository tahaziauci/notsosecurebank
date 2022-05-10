package com.example.Broken.Bank.controller;

import com.example.Broken.Bank.Response.MoneyRequest;
import com.example.Broken.Bank.entity.User;
import com.example.Broken.Bank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.example.Broken.Bank.constand.Constants.CURRENTUSER;

@CrossOrigin(origins = { "http://localhost:3000/" }, allowedHeaders = "*", allowCredentials = "true")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody User user, HttpSession session) {
        session.setAttribute(CURRENTUSER, user.getUsername()); // BAD CODE;
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

    @PostMapping("/deposit")
    public ResponseEntity deposit(HttpSession session, @Valid @RequestBody MoneyRequest moneyRequest) {
        return userService.deposit(session, moneyRequest);
    }

    @GetMapping("/admin")
    public ResponseEntity admin() {
        return userService.getAllUsers();
    }
}
