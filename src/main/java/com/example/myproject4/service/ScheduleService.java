package com.example.myproject4.service;

import com.example.myproject4.dto.ScheduleRequestDto;
import com.example.myproject4.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);

    List<ScheduleResponseDto> findAllSchedules();

    ScheduleResponseDto findScheduleById(Long id);
}
