package com.example.h2h_project.repository;

import com.example.h2h_project.model.Coach;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class CoachRepository {

    private final JdbcTemplate jdbcTemplate;

    public CoachRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Coach> coachRowMapper = (rs, rowNum) -> {
        Coach coach = new Coach();
        coach.setId(rs.getLong("id"));
        coach.setUserId(rs.getLong("user_id"));
        coach.setCertificationLevel(rs.getString("certification_level"));
        coach.setYearsExperience(rs.getInt("years_experience"));
        coach.setSpecialization(rs.getString("specialization"));
        coach.setBio(rs.getString("bio"));
        coach.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // User fields if joined
        try {
            coach.setEmail(rs.getString("email"));
            coach.setFirstName(rs.getString("first_name"));
            coach.setLastName(rs.getString("last_name"));
        } catch (Exception e) {
            // User fields not in result set
        }

        return coach;
    };

    public Long create(Coach coach) {
        String sql = "INSERT INTO coaches (user_id, certification_level, years_experience, specialization, bio) VALUES (?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, coach.getUserId());
            ps.setString(2, coach.getCertificationLevel());
            ps.setInt(3, coach.getYearsExperience() != null ? coach.getYearsExperience() : 0);
            ps.setString(4, coach.getSpecialization());
            ps.setString(5, coach.getBio());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public Optional<Coach> findById(Long id) {
        String sql = "SELECT c.*, u.email, u.first_name, u.last_name " +
                     "FROM coaches c " +
                     "JOIN users u ON c.user_id = u.id " +
                     "WHERE c.id = ?";
        try {
            Coach coach = jdbcTemplate.queryForObject(sql, coachRowMapper, id);
            return Optional.ofNullable(coach);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Coach> findByUserId(Long userId) {
        String sql = "SELECT c.*, u.email, u.first_name, u.last_name " +
                     "FROM coaches c " +
                     "JOIN users u ON c.user_id = u.id " +
                     "WHERE c.user_id = ?";
        try {
            Coach coach = jdbcTemplate.queryForObject(sql, coachRowMapper, userId);
            return Optional.ofNullable(coach);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Coach> findAll() {
        String sql = "SELECT c.*, u.email, u.first_name, u.last_name " +
                     "FROM coaches c " +
                     "JOIN users u ON c.user_id = u.id";
        return jdbcTemplate.query(sql, coachRowMapper);
    }

    public void update(Coach coach) {
        String sql = "UPDATE coaches SET certification_level = ?, years_experience = ?, specialization = ?, bio = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            coach.getCertificationLevel(),
            coach.getYearsExperience(),
            coach.getSpecialization(),
            coach.getBio(),
            coach.getId()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM coaches WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
