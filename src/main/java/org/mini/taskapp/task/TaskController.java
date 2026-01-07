package org.mini.taskapp.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.task.dto.CreateTaskRequest;
import org.mini.taskapp.task.dto.TaskResponseDTO;
import org.mini.taskapp.task.dto.UpdateTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;

    /**
     * Helper method to extract the authenticated user from JWT token.
     * The JWT filter extracts the username from the token and stores it in SecurityContext.
     * @return User object of the authenticated user
     * @throws UserNotFoundException if user not found in database
     */
    private User getAuthenticatedUser() {
        // Extract authentication from SecurityContext (set by JWT filter)
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // Get username from JWT token
        String username = authentication.getName();

        // Fetch user from database
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found: " + username));
    }

    /**
     * POST /api/tasks - Create a new task for the authenticated user.
     * The userId is extracted from the JWT token to ensure task ownership.
     * @param request CreateTaskRequest containing title and content
     * @return TaskResponseDTO with created task details
     */
    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @RequestBody @Valid CreateTaskRequest request) {

        // Extract userId from JWT - this establishes ownership
        User currentUser = getAuthenticatedUser();
        TaskResponseDTO task = taskService.createTask(currentUser.getId(), request);
        
        return ResponseEntity.status(201).body(task);
    }

    /**
     * GET /api/tasks - Get all tasks owned by the authenticated user.
     * Only returns tasks where userId matches the JWT token user.
     * @return List of TaskResponseDTO containing user's tasks
     */
    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getMyTasks() {

        // Extract userId from JWT - ensures we only return this user's tasks
        User currentUser = getAuthenticatedUser();
        List<TaskResponseDTO> tasks = taskService.getMyTasks(currentUser.getId());
        
        return ResponseEntity.ok(tasks);
    }

    /**
     * PUT /api/tasks/{taskId} - Update a task owned by the authenticated user.
     * The service layer verifies ownership before allowing the update.
     * @param taskId The ID of the task to update
     * @param request UpdateTaskRequest containing new title and content
     * @return TaskResponseDTO with updated task details
     * @throws TaskNotFoundException if task doesn't exist or user doesn't own it (handled in service)
     */
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable String taskId,
            @RequestBody @Valid UpdateTaskRequest request) {

        // Extract userId from JWT - passed to service for ownership check
        User currentUser = getAuthenticatedUser();
        TaskResponseDTO task = taskService.updateTask(taskId, request, currentUser.getId());
        
        return ResponseEntity.ok(task);
    }

    /**
     * DELETE /api/tasks/{taskId} - Delete a task owned by the authenticated user.
     * The service layer verifies ownership before allowing the deletion.
     * @param taskId The ID of the task to delete
     * @return 204 No Content on successful deletion
     * @throws TaskNotFoundException if task doesn't exist or user doesn't own it (handled in service)
     */
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {

        // Extract userId from JWT - passed to service for ownership check
        User currentUser = getAuthenticatedUser();
        taskService.deleteTask(taskId, currentUser.getId());
        
        return ResponseEntity.noContent().build();
    }
}

