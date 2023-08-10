package com.deliktas.internshipproject.auth.service;

import com.deliktas.internshipproject.auth.model.*;
import com.deliktas.internshipproject.auth.repository.UserRepository;
import com.deliktas.internshipproject.exceptions.UserAlreadyExistsException;
import com.deliktas.internshipproject.mapper.EntityMapper;
import com.deliktas.internshipproject.mapper.EntityMapperCustomImpl;
import com.deliktas.internshipproject.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private  UserMapper userMapper;

    public AuthenticationResponse register(RegisterRequest registerRequest) {

        try {

            User user = userRepository.findByEmail(registerRequest.getEmail());

            if(user != null)
                throw new UserAlreadyExistsException("User already exists.");

            user = User
                    .builder()
                    .firstName(registerRequest.getFirstName())
                    .lastName(registerRequest.getLastName())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(Role.USER)
                    .build();

            userRepository.save(user);

            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder().token(jwtToken).build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return AuthenticationResponse.builder().token("USER ALREADY EXISTS").build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));

        var user = userRepository.findByEmail(authenticationRequest.getEmail());

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public ResponseEntity<UserDTO> getUserById(Integer id) {

        Optional<User> user = userRepository.findById(id);

                if(user.isPresent()){
                    User existingUser = user.get();
                    return new ResponseEntity<>(userMapper.userToUserDTO(existingUser),HttpStatus.OK);
                }
                else {
                    return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
                }
    }

    public ResponseEntity<UserDTO> getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);

        if(user != null){
            return new ResponseEntity<>(userMapper.userToUserDTO(user),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new UserDTO(), HttpStatus.BAD_REQUEST);
        }

    }
}
