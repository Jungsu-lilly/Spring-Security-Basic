package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class AccountDto {

    private String username;
    private String password;
    private String email;
    private String age;
    private String role;
}
