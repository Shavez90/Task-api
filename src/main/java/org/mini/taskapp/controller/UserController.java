package org.mini.taskapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mini.taskapp.dto.CreateUserRequest;
import org.mini.taskapp.dto.UserDTO;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/users")
@RequiredArgsConstructor
public class UserController {

 private final UserService userService;
 private final UserRepository userRepository;
    @PostMapping("/register")
    public UserDTO createUser(@RequestBody @Valid CreateUserRequest request) {
        return userService.createUser(request);
    }

    // ================= GET CURRENT USER (JWT) =================
    // For logged-in USER or ADMIN to fetch own profile

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping
    public UserDTO getCurrentUser(Authentication authentication) {
        return userService.getUserByUsername(authentication.getName());
    }
}
