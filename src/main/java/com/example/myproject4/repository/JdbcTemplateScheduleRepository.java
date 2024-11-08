package com.example.myproject4.repository;

import com.example.myproject4.dto.ScheduleListRequestDto;

import com.example.myproject4.dto.ScheduleResponseDto;
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
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        // INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id") // 테이블이름이 schedule이고 키칼럼이 id인 sql을 생성
                .usingColumns("things_to_do", "name", "password", "create_date", "update_date");

        // DB에 있는 값과 Schedule에 있는 값의 이름을 매칭시킨다.
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("things_to_do", schedule.getThingsToDo());
        parameters.put("name", schedule.getName());
        parameters.put("password", schedule.getPassword());
        parameters.put("create_date", schedule.getCreateDate());
        parameters.put("update_date", schedule.getUpdateDate());

        // DB에서 자동 생성되는 id(key값)를 Number 타입으로 가져온다.
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(key.longValue(), schedule.getThingsToDo(), schedule.getName(), schedule.getCreateDate(), schedule.getUpdateDate());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(ScheduleListRequestDto dto) {
        StringBuilder query = new StringBuilder("SELECT * FROM schedule WHERE 1=1");    // DB(SQL 테이블)에 작성될 SQL문을 StringBuilder를 통해 query에 저장한다.
        List<Object> params = new ArrayList<>();    // ?에 맞는 데이터를 DB에 전달하기 위해 리스트를 생성한다. (?가 2개 이상이니까 리스트를 생성한다.)

        // 요청할 때 작성자가 있으면, 위에 작성한 query에 " OR name = ?"을 붙여주고 리스트인 params에 넣어준다.
        if (dto.getName() != null) {
            query.append(" OR name = ?");
            params.add(dto.getName());
        }

        // 요청할 때 수정일이 있으면, 위에 작성한 query에 " OR DATE_FORMAT(update_date, '%Y-%m-%d') = ?" 를 붙여주고 리스트인 params에 넣어준다.
        if (dto.getUpdateDate() != null) {
            query.append(" OR DATE_FORMAT(update_date, '%Y-%m-%d') = ?");
            params.add(dto.getUpdateDate());
        }

        // 수정일 기준 내림차순 설정
        query.append(" ORDER BY update_date DESC;");

        //scheduleRowMapper를 통해 DB의 데이터를 ScheduleResponseDto에 넣어주고, 그 데이터를 사용자가 조회할 수 있도록 반환한다.
        return jdbcTemplate.query(query.toString(), params.toArray(), scheduleRowMapper());
    }

    // Null값을 안전하게 다루기 위해 Optional을 사용한다.(하지만, Optional은 항상 추가적인 검증이 필요하다.)
    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    // Optional로 인해 여러 곳에서 사용되는 추가적인 검증 코드를 줄이기 위해, 검증을 하는 메서드를 새로 만든다.
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));    // 조회된 일정이 없는 경우 예외 발생
    }

    @Override
    public int updateSchedule(Long id, String thingsToDo, String name, String password) {
        return jdbcTemplate.update("update schedule set things_to_do = ?, name = ?, update_date = ? where id = ? AND password = ?", thingsToDo, name, LocalDateTime.now(), id, password);
    }

    @Override
    public int deleteSchedule(Long id, String password) {
        return jdbcTemplate.update("delete from schedule where id = ? AND password = ?", id, password);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {

        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {

                // SQL 실행 결과인 ResultSet인 rs에서 데이터를 읽어와서 ScheduleResponseDto 생성자를 호출하여 읽어온 값을 ScheduleResponseDto에 넣어준다.
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("things_to_do"),
                        rs.getString("name"),
                        rs.getTimestamp("create_date").toLocalDateTime(),
                        rs.getTimestamp("update_date").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {

        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
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
