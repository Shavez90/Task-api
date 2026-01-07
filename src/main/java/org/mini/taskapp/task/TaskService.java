package org.mini.taskapp.task;

import lombok.RequiredArgsConstructor;
import org.mini.taskapp.exception.ForbiddenException;
import org.mini.taskapp.exception.TaskNotFoundException;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.task.dto.CreateTaskRequest;
import org.mini.taskapp.task.dto.TaskResponseDTO;
import org.mini.taskapp.task.dto.UpdateTaskRequest;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Create a new task for the authenticated user.
     * @param userId The ID of the authenticated user from JWT
     * @param request The task creation request containing title and content
     * @return TaskResponseDTO with the created task details
     */
    public TaskResponseDTO createTask(String userId, CreateTaskRequest request) {
        // Verify the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Create task with ownership - userId is stored to ensure only this user can access it
        Task task = Task.builder()
                .userId(user.getId())  // ✅ Ownership stored - critical for security
                .title(request.getTitle())
                .content(request.getContent())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        return mapToDto(taskRepository.save(task));
    }

    /**
     * Get all tasks owned by the authenticated user.
     * @param userId The ID of the authenticated user from JWT
     * @return List of TaskResponseDTO containing all user's tasks
     */
    public List<TaskResponseDTO> getMyTasks(String userId) {
        // Fetch only tasks belonging to this user - prevents unauthorized access
        List<Task> tasks = taskRepository.findByUserId(userId);
        
        // Convert all tasks to DTOs
        return tasks.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a task owned by the authenticated user.
     * @param taskId The ID of the task to update
     * @param request The update request containing new title and content
     * @param userId The ID of the authenticated user from JWT
     * @return TaskResponseDTO with the updated task details
     * @throws TaskNotFoundException if task doesn't exist
     * @throws ForbiddenException if user doesn't own the task
     */
    public TaskResponseDTO updateTask(String taskId, UpdateTaskRequest request, String userId) {
        // Find task by ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // ✅ CRITICAL SECURITY CHECK: Verify ownership before allowing update
        // This prevents users from modifying other users' tasks
        if (!task.getUserId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to update this task");
        }

        // Update task fields
        task.setTitle(request.getTitle());
        task.setContent(request.getContent());
        task.setUpdatedAt(Instant.now());

        return mapToDto(taskRepository.save(task));
    }

    /**
     * Delete a task owned by the authenticated user.
     * @param taskId The ID of the task to delete
     * @param userId The ID of the authenticated user from JWT
     * @throws TaskNotFoundException if task doesn't exist
     * @throws ForbiddenException if user doesn't own the task
     */
    public void deleteTask(String taskId, String userId) {
        // Find task by ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + taskId));

        // ✅ CRITICAL SECURITY CHECK: Verify ownership before allowing deletion
        // This prevents users from deleting other users' tasks
        if (!task.getUserId().equals(userId)) {
            throw new ForbiddenException("You don't have permission to delete this task");
        }

        // Delete the task
        taskRepository.deleteById(taskId);
    }

    /**
     * Helper method to convert Task entity to TaskResponseDTO.
     * @param task The task entity
     * @return TaskResponseDTO with task details
     */
    private TaskResponseDTO mapToDto(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .content(task.getContent())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
