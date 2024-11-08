package com.example.myproject4.dto;

import com.example.myproject4.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String thingsToDo;
    private String name;
    private String password;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.thingsToDo = schedule.getThingsToDo();
        this.name = schedule.getName();
        this.password = schedule.getPassword();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }

}
