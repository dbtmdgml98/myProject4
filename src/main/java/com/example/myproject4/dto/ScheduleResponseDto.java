package com.example.myproject4.dto;

import com.example.myproject4.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleResponseDto {

    private Long id;
    private String thingsToDo;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.thingsToDo = schedule.getThingsToDo();
        this.name = schedule.getName();
        this.createDate = schedule.getCreateDate();
        this.updateDate = schedule.getUpdateDate();
    }

    public ScheduleResponseDto(Long id, String thingsToDo, String name, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.thingsToDo = thingsToDo;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

}
