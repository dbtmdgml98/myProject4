package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllSchedules();
    Schedule findScheduleById(Long id);
}
