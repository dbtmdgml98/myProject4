package com.example.myproject4.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleListRequestDto {

    public String name;
    public LocalDate updateDate;
}
