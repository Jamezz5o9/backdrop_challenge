package com.example.building_backdrop.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;
    private Boolean is_verified;
    private LocalDateTime createdTime;
    @Column(unique = true, nullable = false)
    private String accountNumber;
    private String bankCode;
    @Column(nullable = false)
    private String accountName;

    public AccountUser(String accountName, String accountNumber, String bankCode){
        this.accountName = accountName;
        this.accountNumber = accountNumber;
        this.bankCode = bankCode;
    }

}
