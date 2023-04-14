package com.example.building_backdrop.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Builder
@Setter
@Getter
public class ApiErrorResponse {
    private String path;
    private ZonedDateTime time;
    private String data;
    private HttpStatus status;
    private Boolean isSuccessful;
}

