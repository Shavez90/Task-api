package org.mini.taskapp.task;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.task.dto.CreateTaskRequest;
import org.mini.taskapp.task.dto.TaskResponseDTO;
import org.mini.taskapp.task.dto.UpdateTaskRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;



//get task  for authtnetciated user

    private User getAuthenticatedUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();


        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found: " + username));
    }// Create task for authenticated user
    @PostMapping
    public TaskResponseDTO createTask(
            @RequestBody @Valid CreateTaskRequest request) {

        User currentUser = getAuthenticatedUser();
        return taskService.createTask(currentUser.getUsername(), request);

    }

    // ================= UPDATE =================

    @PutMapping("/{journalId}")
    public TaskResponseDTO updateJournal(
            @PathVariable String journalId,
            @RequestBody @Valid UpdateTaskRequest request) {

        User currentUser = getAuthenticatedUser();
        return taskService.updateTask(
                journalId,
                currentUser.getUsername(),
                request
        );
    }

    // ================= DELETE =================

    @DeleteMapping("/{journalId}")
    public void deleteJournal(@PathVariable String journalId) {

        User currentUser = getAuthenticatedUser();
        taskService.deleteJournal(journalId, currentUser.getId());
    }

    // ================= READ =================

    @GetMapping
    public List<TaskResponseDTO> getUserJournals() {

        User currentUser = getAuthenticatedUser();
        return taskService.getMyTask(currentUser.getId());
    }

    @GetMapping("/{journalId}")
    public TaskResponseDTO getJournal(@PathVariable String journalId) {
        User currentUser = getAuthenticatedUser();
        return taskService.getTaskById(journalId, currentUser. getId());
    }
}


