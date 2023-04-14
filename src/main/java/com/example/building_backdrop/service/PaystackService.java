package com.example.building_backdrop.service;

import com.example.building_backdrop.utils.Account.Data;

import java.io.IOException;

public interface PaystackService {
    String paystackValidation(String accountNumber, String bankCode) throws IOException;
}
