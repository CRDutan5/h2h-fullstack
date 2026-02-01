package com.example.h2h_project.repository;

import com.example.h2h_project.model.Referee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class RefereeRepository {

    private final JdbcTemplate jdbcTemplate;

    public RefereeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Referee> refereeRowMapper = (rs, rowNum) -> {
        Referee referee = new Referee();
        referee.setId(rs.getLong("id"));
        referee.setUserId(rs.getLong("user_id"));
        referee.setCertificationLevel(rs.getString("certification_level"));
        referee.setYearsExperience(rs.getInt("years_experience"));
        referee.setBio(rs.getString("bio"));
        referee.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // User fields if joined
        try {
            referee.setEmail(rs.getString("email"));
            referee.setFirstName(rs.getString("first_name"));
            referee.setLastName(rs.getString("last_name"));
        } catch (Exception e) {
            // User fields not in result set
        }

        return referee;
    };

    public Long create(Referee referee) {
        String sql = "INSERT INTO referees (user_id, certification_level, years_experience, bio) VALUES (?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, referee.getUserId());
            ps.setString(2, referee.getCertificationLevel());
            ps.setInt(3, referee.getYearsExperience() != null ? referee.getYearsExperience() : 0);
            ps.setString(4, referee.getBio());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public Optional<Referee> findById(Long id) {
        String sql = "SELECT r.*, u.email, u.first_name, u.last_name " +
                     "FROM referees r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.id = ?";
        try {
            Referee referee = jdbcTemplate.queryForObject(sql, refereeRowMapper, id);
            return Optional.ofNullable(referee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Referee> findByUserId(Long userId) {
        String sql = "SELECT r.*, u.email, u.first_name, u.last_name " +
                     "FROM referees r " +
                     "JOIN users u ON r.user_id = u.id " +
                     "WHERE r.user_id = ?";
        try {
            Referee referee = jdbcTemplate.queryForObject(sql, refereeRowMapper, userId);
            return Optional.ofNullable(referee);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Referee> findAll() {
        String sql = "SELECT r.*, u.email, u.first_name, u.last_name " +
                     "FROM referees r " +
                     "JOIN users u ON r.user_id = u.id";
        return jdbcTemplate.query(sql, refereeRowMapper);
    }

    public void update(Referee referee) {
        String sql = "UPDATE referees SET certification_level = ?, years_experience = ?, bio = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            referee.getCertificationLevel(),
            referee.getYearsExperience(),
            referee.getBio(),
            referee.getId()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM referees WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
