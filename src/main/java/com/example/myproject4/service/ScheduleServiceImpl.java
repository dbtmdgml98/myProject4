package com.example.myproject4.service;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleRequestDto;
import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.Schedule;
import com.example.myproject4.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻.
 * Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.
 * 명시적으로 Service Layer 라는것을 나타낸다.
 * 비지니스 로직을 수행한다.
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {

        // 요청 받은 데이터로 SCHEDULE 객체 생성 ID 없음
        Schedule schedule = new Schedule(dto.getThingsToDo(), dto.getName(), dto.getPassword());

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleListRequestDto dto) {

        return scheduleRepository.findAllSchedules(dto);
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {

        // DB에서 데이터 조회하고 반환 (일정이 있는 경우에만 반환)
        Schedule schedule  = scheduleRepository.findScheduleByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String thingsToDo, String name, String password) {

        // 필수값 검증
        if (thingsToDo == null || name == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The thingsToDo and name are required values.");
        }

//        // 비밀번호 확인
//        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);
//        Schedule savedSchedule = optionalSchedule.orElseThrow(() -> new IllegalArgumentException("아이디를 확인해주세요"));
//        if ( !savedSchedule.getPassword().equals(password)) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 잘못 되었습니다.");
//        }

        // schedule 수정 및 갯수 반환
        int updatedRow = scheduleRepository.updateSchedule(id, thingsToDo, name, password);

        // 수정된 row가 0개라면
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been modified.");
        }

        // 수정된 일정 DB에서 데이터 조회하고 반환 (일정이 있는 경우에만 반환)
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteSchedule(Long id, String password) {

//        // 비밀번호 확인
//        Optional<Schedule> optionalSchedule = scheduleRepository.findScheduleById(id);
//        Schedule savedSchedule = optionalSchedule.orElseThrow(() -> new IllegalArgumentException("아이디를 확인해주세요"));
//        if ( !savedSchedule.getPassword().equals(password)) {
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 잘못 되었습니다.");
//        }

        // schedule 삭제 및 갯수 반환
        int deletedRow = scheduleRepository.deleteSchedule(id, password);

        // 삭제된 row가 0개라면
        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No data has been deleted.");
        }
    }
}
