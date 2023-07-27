package com.deliktas.internshipproject.auth.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationRequest {

    private String email;

    private String password;
}
