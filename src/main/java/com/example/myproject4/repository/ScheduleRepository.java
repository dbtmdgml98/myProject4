package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleListResponseDto;
import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.CheckSchedule;
import com.example.myproject4.entity.ReturnSchedule;
import com.example.myproject4.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleListResponseDto saveSchedule(ReturnSchedule returnSchedule);

    List<ScheduleListResponseDto> findAllSchedules(ScheduleListRequestDto dto);

    Optional<CheckSchedule> findScheduleById(Long id);

    CheckSchedule findScheduleByIdOrElseThrow(Long id);

    int updateSchedule(Long id, String thingsToDo, String name);

    int deleteSchedule(Long id);
}
