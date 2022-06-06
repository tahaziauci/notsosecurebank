package com.example.Broken.Bank.service;

import com.example.Broken.Bank.Response.ErrorResponse;
import com.example.Broken.Bank.Response.HandleMoneyResponse;
import com.example.Broken.Bank.Response.MoneyRequest;
import com.example.Broken.Bank.Response.SuccessResponse;
import com.example.Broken.Bank.entity.User;
import com.example.Broken.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static com.example.Broken.Bank.constants.Constants.CURRENTUSER;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity saveNewUser(User user) {
        String username = user.getUsername();
        BigDecimal balance = user.getBalance();

        // check duplicate user
        if (userRepository.existsUserByUsername(username) || balance == null || !user.getPassword().matches("^[a-z0-9_\\-\\.]+$")) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Bad request: account values are invalid. Try again!")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        // add new user
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUser = User.builder()
                .username(username).password(hashedPassword).balance(balance).build();

        userRepository.save(newUser);

        SuccessResponse msg = SuccessResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Register success")
                .timestamp(new Date())
                .build();

        return ResponseEntity.ok().body(msg);
    }

    public ResponseEntity checkUser(User user, HttpSession session) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (!userRepository.existsUserByUsername(username) || !password.matches("^[a-z0-9_\\-\\.]+$")
                || !bCryptPasswordEncoder.matches(password, userRepository.findUserByUsername(username).getPassword())) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("Username or password is not correct. Please check again")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        // Login Success;
        session.setAttribute(CURRENTUSER, username); // store it in session, so that we can authorize other APIs.
        SuccessResponse msg = SuccessResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Login Success!")
                .timestamp(new Date())
                .build();
        return ResponseEntity.ok(msg);
    }

    @Override
    public ResponseEntity viewUserBalance(HttpSession session) {
        String userName = (String) session.getAttribute(CURRENTUSER);
        if (userName == null || !userRepository.existsUserByUsername(userName)) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("User is not authorized. Please sign in!")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        User user = userRepository.findUserByUsername(userName);
        HandleMoneyResponse handleMoneyResponse = HandleMoneyResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Find the account!")
                .timestamp(new Date())
                .username(user.getUsername())
                .balance(user.getBalance())
                .build();
        return ResponseEntity.ok(handleMoneyResponse);
    }

    @Override
    public ResponseEntity withdraw(HttpSession session, MoneyRequest moneyRequest) {
        String userName = (String) session.getAttribute(CURRENTUSER);
        if (userName == null || !userRepository.existsUserByUsername(userName)
                || !moneyRequest.getUsername().equals(userName)) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("User is not authorized. Please sign in!")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        User user = userRepository.findUserByUsername(userName);
        BigDecimal currentBalance = user.getBalance();
        BigDecimal withdrawAmount = moneyRequest.getAmount();

        if (currentBalance.compareTo(withdrawAmount) == -1) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Withdraw money is invalid!")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        user.setBalance(currentBalance.subtract(withdrawAmount));
        User updatedUser = userRepository.save(user);

        HandleMoneyResponse handleMoneyResponse = HandleMoneyResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message("Withdraw Success")
                .timestamp(new Date())
                .username(updatedUser.getUsername())
                .balance(updatedUser.getBalance())
                .build();
        return ResponseEntity.ok(handleMoneyResponse);
    }

    @Override
    public ResponseEntity deposit(HttpSession session, MoneyRequest moneyRequest) {
        String userName = (String) session.getAttribute(CURRENTUSER);
        if (userName == null || !userRepository.existsUserByUsername(userName) || !moneyRequest.getUsername().equals(userName)) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("User is not authorized. Please sign in!")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        User user = userRepository.findUserByUsername(userName);
        BigDecimal currentBalance = user.getBalance();
        BigDecimal depositAmount = moneyRequest.getAmount();
        BigDecimal total = currentBalance.add(depositAmount);

        if (total.compareTo(BigDecimal.ZERO) > 0 && total.compareTo(BigDecimal.valueOf(4294967295.99)) < 0) {
            user.setBalance(total);
            User updatedUser = userRepository.save(user);

            HandleMoneyResponse handleMoneyResponse = HandleMoneyResponse
                    .builder()
                    .code(HttpStatus.OK.value())
                    .message("Deposit Success")
                    .timestamp(new Date())
                    .username(updatedUser.getUsername())
                    .balance(updatedUser.getBalance())
                    .build();
            return ResponseEntity.ok(handleMoneyResponse);
        }

        ErrorResponse msg = ErrorResponse
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("Account balance exceeds max limit!")
                .timestamp(new Date())
                .build();
        return ResponseEntity.badRequest().body(msg);
    }

    @Override
    public ResponseEntity getAllUsers(){
        List<User> list = userRepository.findAll();
        return ResponseEntity.ok(list);
    }
}
