package org.mini.taskapp.task;


import lombok.RequiredArgsConstructor;
import org.mini.taskapp.exception.UserNotFoundException;
import org.mini.taskapp.model.User;
import org.mini.taskapp.repository.UserRepository;
import org.mini.taskapp.task.dto.CreateTaskRequest;
import org.mini.taskapp.task.dto.TaskResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class service {
  private TaskRepository taskrepository;
  private UserRepository userRepository;

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

       return mapToDto(taskrepository.save(task));
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

