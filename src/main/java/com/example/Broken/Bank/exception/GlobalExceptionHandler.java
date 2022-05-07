package com.example.Broken.Bank.exception;

import com.example.Broken.Bank.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {
    // Cited: https://juejin.cn/post/6938250125743489054
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        ErrorResponse msg;
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            if (!errors.isEmpty()) {
                String message = errors.get(0).getDefaultMessage(); // retrieve the first error
                msg = ErrorResponse
                        .builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .timestamp(new Date())
                        .build();
                return new ResponseEntity<ErrorResponse>(msg, HttpStatus.BAD_REQUEST);
            }
        }
        msg = ErrorResponse
                .builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message("UNKNOWN")
                .timestamp(new Date())
                .build();
        return new ResponseEntity<ErrorResponse>(msg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> globalExceptionHandler(Exception ex) {
        ErrorResponse message = ErrorResponse
                .builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getClass().getSimpleName())
                .timestamp(new Date())
                .build();
        return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
