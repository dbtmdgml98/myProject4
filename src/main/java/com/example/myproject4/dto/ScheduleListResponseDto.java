package com.example.myproject4.dto;

import com.example.myproject4.entity.CheckSchedule;
import com.example.myproject4.entity.ReturnSchedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleListResponseDto {

    private Long id;
    private String thingsToDo;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public ScheduleListResponseDto(ReturnSchedule returnSchedule) {
        this.id = returnSchedule.getId();
        this.thingsToDo = returnSchedule.getThingsToDo();
        this.name = returnSchedule.getName();
        this.createDate = returnSchedule.getCreateDate();
        this.updateDate = returnSchedule.getUpdateDate();
    }
    public ScheduleListResponseDto(CheckSchedule checkSchedule) {
        this.id = checkSchedule.getId();
        this.thingsToDo = checkSchedule.getThingsToDo();
        this.name = checkSchedule.getName();
        this.createDate = checkSchedule.getCreateDate();
        this.updateDate = checkSchedule.getUpdateDate();
    }
}
