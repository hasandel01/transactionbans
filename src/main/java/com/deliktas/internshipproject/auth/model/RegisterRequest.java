package com.deliktas.internshipproject.auth.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

}
