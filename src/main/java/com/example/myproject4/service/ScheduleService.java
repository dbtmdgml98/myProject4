package com.example.myproject4.service;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleListResponseDto;
import com.example.myproject4.dto.ScheduleRequestDto;
import com.example.myproject4.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleListResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleListResponseDto> findAllSchedules(ScheduleListRequestDto dto);

    ScheduleListResponseDto findScheduleById(Long id);

    ScheduleListResponseDto updateSchedule(Long id, String thingsToDo, String name, String password);

    void deleteSchedule(Long id, String password);
}
