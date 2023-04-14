package com.example.building_backdrop.dtos.request;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    private String accountNumber;
    private String accountName;
    private String bankCode;
}
