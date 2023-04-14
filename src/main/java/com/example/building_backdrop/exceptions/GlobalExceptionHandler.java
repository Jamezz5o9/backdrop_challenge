package com.example.building_backdrop.exceptions;

import com.example.building_backdrop.dtos.response.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GenericException.class)
    public ResponseEntity<?> handleGenericException(GenericException genericException, HttpServletRequest httpServletRequest){
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .data(genericException.getMessage())
                .isSuccessful(false)
                .path(httpServletRequest.getRequestURI())
                .time(ZonedDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
