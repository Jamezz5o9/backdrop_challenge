package com.example.building_backdrop.service;

import com.example.building_backdrop.data.model.AccountUser;
import com.example.building_backdrop.dtos.request.AddUserAccountRequest;
import com.example.building_backdrop.dtos.request.GetUserAccountNameRequest;
import com.example.building_backdrop.dtos.request.UserRegistrationRequest;
import com.example.building_backdrop.dtos.response.ApiData;

import java.io.IOException;
import java.util.Optional;

public interface UserService {
    Optional<AccountUser> findUserById(String userId);
    Optional<AccountUser> findUserByAccountNumber(String accountNumber);

    public ApiData addUserBankAccount(AddUserAccountRequest addUserAccountRequest) throws IOException;
    public ApiData getUserAccountName(GetUserAccountNameRequest getUserAccountNameRequest) throws IOException ;
}
