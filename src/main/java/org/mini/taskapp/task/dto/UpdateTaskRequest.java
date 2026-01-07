package org.mini.taskapp.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTaskRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
