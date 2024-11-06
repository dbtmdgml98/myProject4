package com.example.myproject4.dto;

import com.example.myproject4.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String thingsToDo;
    private String name;
    private String password;
    private String dateOfWriting;
    private String dateOfUpdating;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.thingsToDo = schedule.getThingsToDo();
        this.name = schedule.getName();;
        this.password = schedule.getPassword();
        this.dateOfWriting = schedule.getDateOfWriting();
        this.dateOfUpdating = schedule.getDateOfUpdating();
    }
}
