package org.mini.taskapp.task;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.task.dto.CreateTaskRequest;
import org.mini.taskapp.task.dto.TaskResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/task")
@RequiredArgsConstructor
public class TaskController {

    private final service service;
    private final UserRepository userRepository;




    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();


        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found: " + username));
    }
    @PostMapping
    public TaskResponseDTO createTask(
            @RequestBody @Valid CreateTaskRequest request) {

        User currentUser = getAuthenticatedUser();
        return service.createTask(currentUser.getUsername(), request);

    }
}
