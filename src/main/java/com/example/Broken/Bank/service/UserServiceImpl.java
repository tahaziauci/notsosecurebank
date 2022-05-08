package com.example.Broken.Bank.service;

import com.example.Broken.Bank.Response.ErrorResponse;
import com.example.Broken.Bank.Response.HandleMoneyResponse;
import com.example.Broken.Bank.Response.SuccessResponse;
import com.example.Broken.Bank.entity.User;
import com.example.Broken.Bank.model.UserModel;
import com.example.Broken.Bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

import static com.example.Broken.Bank.constand.Constants.CURRENTUSER;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public ResponseEntity saveNewUser(UserModel userModel) {
        String username = userModel.getUsername();
        String password = userModel.getPassword();  // TODO: for FIX, we need to hash password and store in DB: passwordEncoder.encode(password)
        BigDecimal balance = userModel.getBalance();

        // check duplicate user
        if (userRepository.existsUserByUsername(username)) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("User exists already")
                    .timestamp(new Date())
                    .build();
            return ResponseEntity.badRequest().body(msg);
        }

        // add new user
        User user = User.builder()
                .username(username).password(password).balance(balance).build();

        userRepository.save(user);

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
        if (!userRepository.existsUserByUsernameAndPassword(username, password)) {
            ErrorResponse msg = ErrorResponse
                    .builder()
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message("User or password is not correct.Please check again")
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
}