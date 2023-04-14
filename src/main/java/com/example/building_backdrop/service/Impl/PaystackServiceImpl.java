package com.example.building_backdrop.service.Impl;

import com.example.building_backdrop.service.PaystackService;
import com.example.building_backdrop.utils.Account.Account;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PaystackServiceImpl  implements PaystackService {
    @Override
    public String paystackValidation(String accountNumber, String bankCode) throws IOException {
        OkHttpClient client = new OkHttpClient();

        String SECRET_KEY = "sk_test_e70608f50daa9bc464611958f07d613ce25c496b";
        Request request = new Request.Builder()
                .url("https://api.paystack.co/bank/resolve?account_number="
                        + accountNumber + "&bank_code="
                        + bankCode)
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY)
                .build();


        try (ResponseBody response = client.newCall(request).execute().body()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .readValue(response.string(), Account.class).getData().getAccount_name();
        }
    }
}
