package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules(ScheduleListRequestDto dto);

    Optional<Schedule> findScheduleById(Long id);

    int updateSchedule(Long id, String thingsToDo, String name);

    int deleteSchedule(Long id);
}
