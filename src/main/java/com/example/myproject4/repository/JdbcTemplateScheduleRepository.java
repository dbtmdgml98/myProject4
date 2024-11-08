package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleListRequestDto;
import com.example.myproject4.dto.ScheduleListResponseDto;
import com.example.myproject4.dto.ScheduleResponseDto;
import com.example.myproject4.entity.CheckSchedule;
import com.example.myproject4.entity.ReturnSchedule;
import com.example.myproject4.entity.Schedule;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleListResponseDto saveSchedule(ReturnSchedule returnSchedule) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id")
                .usingColumns("things_to_do", "name", "create_date", "update_date");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("things_to_do", returnSchedule.getThingsToDo());
        parameters.put("name", returnSchedule.getName());
        parameters.put("create_date", returnSchedule.getCreateDate());
        parameters.put("update_date", returnSchedule.getUpdateDate());

        // 저장 후 생성된 key값 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleListResponseDto(key.longValue(), returnSchedule.getThingsToDo(), returnSchedule.getName(), returnSchedule.getCreateDate(), returnSchedule.getUpdateDate());
    }

//    @Override
//    public List<ScheduleResponseDto> findAllSchedules(ScheduleListRequestDto dto) {
//        return jdbcTemplate.query("select * from schedule where name = ? or (where update_date >= ? and update_date < ?)order by update_date", scheduleRowMapper(), dto.getName(), dto.getUpdate_date());
//    }

    @Override
    public List<ScheduleListResponseDto> findAllSchedules(ScheduleListRequestDto dto) {
        StringBuilder query = new StringBuilder("SELECT * FROM schedule");
        List<Object> params = new ArrayList<>();
        boolean hasConditions = false;
        if (dto.getName() != null && !dto.getName().isEmpty()) {
            query.append(" WHERE name = ?");
            params.add(dto.getName());
            hasConditions = true;
        }
        if (dto.getUpdate_date() != null) {
            LocalDate startDate = dto.getUpdate_date();
            LocalDateTime startOfDay = startDate.atStartOfDay();
            LocalDateTime endOfDay = startOfDay.plusDays(1);
            if (hasConditions) {
                query.append(" AND update_date >= ? AND update_date < ?");
            } else {
                query.append(" WHERE update_date >= ? AND update_date < ?");
            }
            params.add(startOfDay);
            params.add(endOfDay);
        }
        query.append(" ORDER BY update_date");
        return jdbcTemplate.query(query.toString(), scheduleRowMapper(), params.toArray());
    }

    @Override
    public Optional<CheckSchedule> findScheduleById(Long id) {
        List<CheckSchedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public CheckSchedule findScheduleByIdOrElseThrow(Long id) {
        List<CheckSchedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));    // 조회된 일정이 없는 경우 예외 발생
    }

    @Override
    public int updateSchedule(Long id, String thingsToDo, String name) {
        return jdbcTemplate.update("update schedule set things_to_do = ?, name = ?, update_date = now() where id = ?", thingsToDo, name, id);
    }

    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where id = ?", id);
    }

    private RowMapper<ScheduleListResponseDto> scheduleRowMapper() {

        return new RowMapper<ScheduleListResponseDto>() {
            @Override
            public ScheduleListResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleListResponseDto(
                        rs.getLong("id"),
                        rs.getString("things_to_do"),
                        rs.getString("name"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<CheckSchedule> scheduleRowMapperV2() {

        return new RowMapper<CheckSchedule>() {
            @Override
            public CheckSchedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new CheckSchedule(
                        rs.getLong("id"),
                        rs.getString("things_to_do"),
                        rs.getString("name"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }
}
