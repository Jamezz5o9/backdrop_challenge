package com.example.building_backdrop.data.controller;

import com.example.building_backdrop.data.repository.UserRepository;
import com.example.building_backdrop.dtos.request.AddUserAccountRequest;
import com.example.building_backdrop.dtos.request.GetUserAccountNameRequest;
import com.example.building_backdrop.dtos.request.UserRegistrationRequest;
import com.example.building_backdrop.dtos.response.ApiData;
import com.example.building_backdrop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @MutationMapping
    public ApiData addUserBankAccount(@Argument AddUserAccountRequest addUserAccountRequest) throws IOException {
        return userService.addUserBankAccount(addUserAccountRequest);
    }

    @QueryMapping
    public ApiData getUserAccountName(@Argument GetUserAccountNameRequest getUserAccountNameRequest) throws IOException {
        return userService.getUserAccountName(getUserAccountNameRequest);

    }
}
