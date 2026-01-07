package org.mini.taskapp.task;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "task")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    @Id
    private String id;
    public  String userId;
    private String title;
    private String content;

    private Instant createdAt;

    private Instant updatedAt;
}
