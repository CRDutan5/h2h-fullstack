package com.example.h2h_project.repository;

import com.example.h2h_project.model.Referee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class RefereeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RefereeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createReferee(Referee referee) {
        String sql = "INSERT INTO referees (user_id, certification_level, years_experience) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setLong(1, referee.getUserId());

            if (referee.getCertificationLevel() != null) {
                ps.setString(2, referee.getCertificationLevel());
            } else {
                ps.setNull(2, java.sql.Types.VARCHAR);
            }

            ps.setInt(3, referee.getYearsExperience());

            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }
}
