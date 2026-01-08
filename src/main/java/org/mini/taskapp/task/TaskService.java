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
  private  final UserRepository userRepository;
/// /create
   public TaskResponseDTO createTask(String username, CreateTaskRequest request){
       User user = userRepository.findByUsername(username)
               .orElseThrow(() -> new UserNotFoundException("User not found"));

       Task task = Task.builder()
               .userId(user.getId())               // ✅ ownership stored internally
               .title(request.getTitle())
               .content(request.getContent())
               .createdAt(Instant.now())
               .updatedAt(Instant.now())
               .build();

       return mapToDto(taskRepository.save(task));
   }
    // GET MY JOURNALS (JWT user → DB user → journals)
    public List<TaskResponseDTO> getMyTask(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return taskRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToDto)
                .toList();
    }
    // GET SINGLE JOURNAL (ownership check)
    public TaskResponseDTO getTaskById(String journalId, String userId) {
        Task task = taskRepository.findById(journalId)
                .orElseThrow(() -> new TaskNotFoundException("Journal not found"));

        if (!task.getUserId().equals(userId)) {
            throw new ForbiddenException("You do not own this journal");
        }

        return mapToDto(task);
    }

    // UPDATE JOURNAL (ownership enforced)
    public TaskResponseDTO updateTask(
            String journalId,
            String username,
            UpdateTaskRequest request

    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

       Task task = taskRepository.findById(journalId)
                .orElseThrow(() -> new TaskNotFoundException("task not found"));

        if (!task.getUserId().equals(user.getId())) {
            throw new ForbiddenException("You do not own this task");
        }

        task.setTitle(request.getTitle());
        task.setContent(request.getContent());
        task.setUpdatedAt(Instant.now());

        return mapToDto(taskRepository.save(task));
    }

    // DELETE JOURNAL (ownership enforced)
    public void deleteJournal(String journalId, String userid) {

      Task task = taskRepository.findById(journalId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found"));

        if (!task.getUserId().equals(userid)) {
            throw new ForbiddenException("You do not own this task");
        }

        taskRepository.delete(task);
    }
    // DTO MAPPER
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

