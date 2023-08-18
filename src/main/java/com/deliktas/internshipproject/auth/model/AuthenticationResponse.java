package com.deliktas.internshipproject.auth.model;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String accessToken;


    private String refreshToken;

}
