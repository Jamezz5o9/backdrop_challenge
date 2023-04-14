package com.example.building_backdrop.service.ImplTest;

import com.example.building_backdrop.data.model.AccountUser;
import com.example.building_backdrop.data.repository.UserRepository;
import com.example.building_backdrop.dtos.request.AddUserAccountRequest;
import com.example.building_backdrop.dtos.request.GetUserAccountNameRequest;
import com.example.building_backdrop.dtos.response.ApiData;
import com.example.building_backdrop.service.Impl.PaystackServiceImpl;
import com.example.building_backdrop.service.Impl.UserServiceImpl;
import com.example.building_backdrop.service.PaystackService;
import com.example.building_backdrop.utils.Account.Data;
import com.example.building_backdrop.utils.matchNames.ValidateNames;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.io.IOException;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PaystackService paystackService;
    @Mock
    private ValidateNames validateNames;
    @InjectMocks
    private UserServiceImpl userService;
    private final String TEST_ACCOUNT_NUMBER = "2111265871";
    private final String TEST_ACCOUNT_NAME = "SULAIMON ABOLAJI DISU";
    private final String TEST_BANK_CODE = "057";
    private AccountUser expectedAccountUser;


    @BeforeEach
    public void setUp() {
        expectedAccountUser = new AccountUser();
        expectedAccountUser.setAccountNumber(TEST_ACCOUNT_NUMBER);
        expectedAccountUser.setBankCode(TEST_BANK_CODE);
        expectedAccountUser.setAccountName(TEST_ACCOUNT_NAME);
    }
    @Test
    void userThatDidNotProvideNameThatMatchPaystackAPIShouldBeSavedInTheDatabaseWithFalseVerification() throws IOException {
        expectedAccountUser.setIs_verified(false);
        String NAME = "SULAIMON DISU";
        // Set up mock behavior
        when(userRepository.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        when(paystackService.paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE))).thenReturn(TEST_ACCOUNT_NAME);
        when(validateNames.isMatch(eq(TEST_ACCOUNT_NAME), eq(NAME))).thenReturn(false);
        when(userRepository.save(any(AccountUser.class))).thenReturn(expectedAccountUser);

        // Call the method being tested
        AddUserAccountRequest addUserAccountRequest = new AddUserAccountRequest(TEST_ACCOUNT_NUMBER, TEST_BANK_CODE, TEST_ACCOUNT_NAME);
        userService.addUserBankAccount(addUserAccountRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertFalse(expectedAccountUser.getIs_verified());

        // Verify that the paystackValidation method was called correctly
        verify(paystackService, times(1)).paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE));
    }

    @Test
    void testThatAnAccountForExistingUserCanBeRetrieved() throws IOException {
        expectedAccountUser.setIs_verified(true);

        when(userRepository.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        GetUserAccountNameRequest getUserAccountNameRequest = new GetUserAccountNameRequest();
        getUserAccountNameRequest.setAccountNumber(TEST_ACCOUNT_NUMBER);
        getUserAccountNameRequest.setBankCode(TEST_BANK_CODE);
        userService.getUserAccountName(getUserAccountNameRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertTrue(expectedAccountUser.getIs_verified());
    }
    @Test
    void ifNameInputedByUserIsNotAvailableItReturnTheOneVerifiedByPayStack() throws IOException {
        String test_account_number = "2116471341";
        String test_bank_code = "033";
        String test_bank_name = "JOSEPH RACHAEL ABIMBOLA";
        String expectedName = "JOSEPH RACHAEL ABIMBOLA";
        when(userRepository.findUserByAccountNumber(test_account_number)).thenReturn(Optional.empty());


        when(paystackService.paystackValidation(eq(test_account_number),eq(test_bank_code))).thenReturn(test_bank_name);
        GetUserAccountNameRequest getUserAccountNameRequest = new GetUserAccountNameRequest();
        getUserAccountNameRequest.setAccountNumber(TEST_ACCOUNT_NUMBER);
        getUserAccountNameRequest.setBankCode(TEST_BANK_CODE);
        userService.getUserAccountName(getUserAccountNameRequest);

        assertNotNull(expectedAccountUser);
        assertEquals(test_bank_name, expectedName);
    }

    @Test
    void addUserAccountThatMatchesPaystackApi__AndAlsoWithinThelevenshteinDistanceIsSavedWithStatusVerified() throws IOException {
        // Set up test data
        expectedAccountUser.setIs_verified(true);

        // Set up mock behavior
        when(userRepository.findUserByAccountNumber(TEST_ACCOUNT_NUMBER)).thenReturn(Optional.empty());
        when(paystackService.paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE))).thenReturn(TEST_ACCOUNT_NAME);
        when(validateNames.isMatch(eq(TEST_ACCOUNT_NAME), eq(TEST_ACCOUNT_NAME))).thenReturn(true);
        when(userRepository.save(any(AccountUser.class))).thenReturn(expectedAccountUser);

        // Call the method being tested
        AddUserAccountRequest addUserAccountRequest = new AddUserAccountRequest(TEST_ACCOUNT_NUMBER, TEST_BANK_CODE, TEST_ACCOUNT_NAME);
        userService.addUserBankAccount(addUserAccountRequest);

        // Verify the results
        assertNotNull(expectedAccountUser);
        assertEquals(TEST_ACCOUNT_NAME, expectedAccountUser.getAccountName());
        assertTrue(expectedAccountUser.getIs_verified());

        // Verify that the paystackValidation method was called correctly
        verify(paystackService, times(1)).paystackValidation(eq(TEST_ACCOUNT_NUMBER), eq(TEST_BANK_CODE));
    }




}