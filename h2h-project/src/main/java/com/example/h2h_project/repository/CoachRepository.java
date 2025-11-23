package com.example.h2h_project.repository;

import com.example.h2h_project.model.Coach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CoachRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoachRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createCoach(Coach coach) {
        String sql = "INSERT INTO coaches (user_id, team_id, certification_level, years_experience) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});

            ps.setLong(1, coach.getUserId());

            if (coach.getTeamId() != null) {
                ps.setLong(2, coach.getTeamId());
            } else {
                ps.setNull(2, java.sql.Types.BIGINT);
            }

            if (coach.getCertificationLevel() != null) {
                ps.setString(3, coach.getCertificationLevel());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }

            ps.setInt(4, coach.getYearsExperience());

            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }
}
