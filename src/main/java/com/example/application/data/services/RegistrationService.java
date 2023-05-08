package com.example.application.data.services;

import com.example.application.data.entity.UserRole;
import com.example.application.data.entity.Users;
import com.example.application.security.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;

    public void register(RegistrationRequest request) {
        userService.signUpUser(
                new Users(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getUsername(),
                        request.getPassword(),
                        UserRole.USER
                )
        );
    }

    public void signup(Users user)
    {
        userService.signUpUser(user);
    }
}
