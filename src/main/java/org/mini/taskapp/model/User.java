package org.mini.taskapp.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    public String username;
    public String password;

    public String id;

    private List<String> taskIds;

    private Instant createdAt;
    private Role role;
    public  String  email;

}


/*
* package com.journalapi.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    private List<String> journalIds;

    private Instant createdAt;
    private Role role;

}
*/