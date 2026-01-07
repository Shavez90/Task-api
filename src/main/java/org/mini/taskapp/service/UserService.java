package org.mini.taskapp.service;

import lombok.RequiredArgsConstructor;
import org.mini.taskapp.dto.CreateUserRequest;
import org.mini.taskapp.dto.UserDTO;
import org.mini.taskapp.exception.DuplicateUsernameException;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.Role;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    public  final UserRepository userRepository;
    public UserDTO createUser(CreateUserRequest request) {



        if (userRepository.existsByUsername(request.getUsername())) {
            throw new DuplicateUsernameException(
                    "Username already exists: " + request.getUsername()
            );
        }

        User user = User.builder()
                .username(request.getUsername())

                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return mapToDto(userRepository.save(user));
    }
    public UserDTO getUserByUsername(String  username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return mapToDto(user);
    }


    private UserDTO mapToDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
