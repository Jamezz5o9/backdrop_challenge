package com.example.building_backdrop.service.Impl;

import com.example.building_backdrop.data.model.AccountUser;
import com.example.building_backdrop.data.repository.UserRepository;
import com.example.building_backdrop.dtos.request.AddUserAccountRequest;
import com.example.building_backdrop.dtos.request.GetUserAccountNameRequest;
import com.example.building_backdrop.dtos.response.ApiData;
import com.example.building_backdrop.exceptions.GenericException;
import com.example.building_backdrop.service.PaystackService;
import com.example.building_backdrop.service.UserService;
import com.example.building_backdrop.utils.matchNames.ValidateNames;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidateNames validateNames;

    private final PaystackService paystackService;

    @Override
    public Optional<AccountUser> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public Optional<AccountUser> findUserByAccountNumber(String accountNumber) {
        return userRepository.findUserByAccountNumber(accountNumber);
    }

    @Override
    public ApiData addUserBankAccount(AddUserAccountRequest addUserAccountRequest) throws IOException {

        if(findUserByAccountNumber(addUserAccountRequest.accountNumber()).isPresent())
            throw new GenericException(String.format("User with %s already exist", addUserAccountRequest.accountNumber()));

        String foundAccountName= paystackService.paystackValidation(addUserAccountRequest.accountNumber(), addUserAccountRequest.bankCode());

        boolean nameMatches = validateNames.isMatch(addUserAccountRequest.accountName(), foundAccountName);

        AccountUser foundUser = saveUser(addUserAccountRequest);
        if(!nameMatches){
            return ApiData.builder()
                    .message("Name doesn't match")
                    .build();
        }
        userRepository.updateUser(foundUser.getAccountName());

        return ApiData.builder()
                .message(String.format("%s is VERIFIED", addUserAccountRequest.accountName()))
                .build();

    }
    private AccountUser saveUser(AddUserAccountRequest addUserAccountRequest) {

        AccountUser accountUser = AccountUser.builder()
                .accountNumber(addUserAccountRequest.accountNumber())
                .createdTime(LocalDateTime.now())
                .accountName(addUserAccountRequest.accountName())
                .is_verified(false)
                .bankCode(addUserAccountRequest.bankCode())
                .build();
        return userRepository.save(accountUser);
    }

    @Override
    public ApiData getUserAccountName(GetUserAccountNameRequest getUserAccountNameRequest) throws IOException {

        Optional<AccountUser> accountUser = findUserByAccountNumber(getUserAccountNameRequest.getAccountNumber());
        if(accountUser.isPresent() && accountUser.get().getIs_verified()){

            return ApiData.builder().
                    message(WordUtils.capitalizeFully(accountUser.get().getAccountName()))
                    .build();
        }

        else{
            String foundAccountName = paystackService.paystackValidation(getUserAccountNameRequest.getAccountNumber(), getUserAccountNameRequest.getBankCode());

              return ApiData.builder()
                        .message(WordUtils.capitalizeFully(foundAccountName))
                        .build();
            }
    }

}
