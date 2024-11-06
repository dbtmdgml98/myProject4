package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Annotation @Repository는 @Component와 같다, Spring Bean으로 등록한다는 뜻.
 * Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.
 * 명시적으로 Repository Layer 라는것을 나타낸다.
 * DB와 상호작용하여 데이터를 CRUD하는 작업을 수행한다.
 */
@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    // DB
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @Override
    public Schedule saveSchedule(Schedule schedule) {

        // 식별자 자동 생성
        Long scheduleId = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;
        schedule.setId(scheduleId);

        // 자료구조(DB)에 저장
        scheduleList.put(scheduleId, schedule);

        return schedule;
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {

        // init List
        List<ScheduleResponseDto> allSchedules = new ArrayList<>();

        // HashMap<Schedule> - >List<ScheduleResponseDto>
        for (Schedule schedule : scheduleList.values()) {
            ScheduleResponseDto responseDto = new ScheduleResponseDto(schedule);
            allSchedules.add(responseDto);
        }
        return allSchedules;
    }

    @Override
    public Schedule findScheduleById(Long id) {
        return scheduleList.get(id);
    }
}

