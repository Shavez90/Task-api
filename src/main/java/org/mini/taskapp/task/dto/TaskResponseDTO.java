package org.mini.taskapp.task.dto;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDTO {

    private String id;

    private String title;
    private String content;

    private Instant createdAt;

    private Instant updatedAt;
}