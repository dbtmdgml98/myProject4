package com.example.myproject4.service;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleRequestDto;
import com.example.myproject4.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules(ScheduleListRequestDto dto);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateSchedule(Long id, String thingsToDo, String name, String password);

    void deleteSchedule(Long id, String password);
}
