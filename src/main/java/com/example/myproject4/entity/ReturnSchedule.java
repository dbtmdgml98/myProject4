package com.example.myproject4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReturnSchedule {

    private Long id;
    private String thingsToDo;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String password;

    public ReturnSchedule(String thingsToDo, String name, String password) {
        this.thingsToDo = thingsToDo;
        this.name = name;
        this.createDate = LocalDateTime.now();
        this.updateDate = LocalDateTime.now();
    }

}
