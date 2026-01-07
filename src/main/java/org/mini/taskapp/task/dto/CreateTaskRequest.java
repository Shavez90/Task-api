package org.mini.taskapp.task.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskRequest {
    @NonNull
     private  String title;
    @NonNull
     private String content;
}
