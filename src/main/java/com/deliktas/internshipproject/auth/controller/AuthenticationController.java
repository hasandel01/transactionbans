package com.deliktas.internshipproject.auth.controller;

import com.deliktas.internshipproject.auth.model.AuthenticationRequest;
import com.deliktas.internshipproject.auth.model.RegisterRequest;
import com.deliktas.internshipproject.auth.model.UserDTO;
import com.deliktas.internshipproject.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.deliktas.internshipproject.auth.model.AuthenticationResponse;

import java.io.IOException;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationRequest));
    }

    @GetMapping("user/id/{id}")
    public ResponseEntity<UserDTO> getAUserById(@PathVariable Integer id) {
        return authenticationService.getUserById(id);
    }

    @GetMapping("user/email/{email}")
    public ResponseEntity<UserDTO> getAUserByEmail(@PathVariable String email) {
        return authenticationService.getUserByEmail(email);
    }


    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        authenticationService.refreshToken(request,response);
    }


}
