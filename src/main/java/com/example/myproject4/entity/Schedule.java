package com.example.myproject4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private String thingsToDo;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public Schedule(Long id, String thingsToDo, String name, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.thingsToDo = thingsToDo;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Schedule(String thingsToDo, String name, String password) {
        this.thingsToDo = thingsToDo;
        this.name = name;
        this.password = password;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }
}