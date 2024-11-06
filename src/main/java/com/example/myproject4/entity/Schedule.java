package com.example.myproject4.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private String thingsToDo;
    private String name;
    private String password;
    private String dateOfWriting;
    private String dateOfUpdating;

    public Schedule(String thingsToDo, String name, String password, String dateOfWriting, String dateOfUpdating) {
        this.thingsToDo = thingsToDo;
        this.name = name;
        this.password = password;
        this.dateOfWriting = dateOfWriting;
        this.dateOfUpdating = dateOfUpdating;
    }

}