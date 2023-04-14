package com.example.building_backdrop.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserAccountNameRequest {
    private String accountNumber;
    private String bankCode;
}
