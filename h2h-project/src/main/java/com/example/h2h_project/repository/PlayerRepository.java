package com.example.h2h_project.repository;

import com.example.h2h_project.model.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Player> playerRowMapper = (rs, rowNum) -> {
        Player player = new Player();
        player.setId(rs.getLong("id"));
        player.setUserId(rs.getLong("user_id"));
        player.setPosition(rs.getString("position"));
        player.setSkillLevel(rs.getString("skill_level"));
        player.setYearsExperience(rs.getInt("years_experience"));
        player.setBio(rs.getString("bio"));
        player.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());

        // User fields if joined
        try {
            player.setEmail(rs.getString("email"));
            player.setFirstName(rs.getString("first_name"));
            player.setLastName(rs.getString("last_name"));
        } catch (Exception e) {
            // User fields not in result set
        }

        return player;
    };

    public Long create(Player player) {
        String sql = "INSERT INTO players (user_id, position, skill_level, years_experience, bio) VALUES (?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, player.getUserId());
            ps.setString(2, player.getPosition());
            ps.setString(3, player.getSkillLevel());
            ps.setInt(4, player.getYearsExperience() != null ? player.getYearsExperience() : 0);
            ps.setString(5, player.getBio());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public Optional<Player> findById(Long id) {
        String sql = "SELECT p.*, u.email, u.first_name, u.last_name " +
                     "FROM players p " +
                     "JOIN users u ON p.user_id = u.id " +
                     "WHERE p.id = ?";
        try {
            Player player = jdbcTemplate.queryForObject(sql, playerRowMapper, id);
            return Optional.ofNullable(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Player> findByUserId(Long userId) {
        String sql = "SELECT p.*, u.email, u.first_name, u.last_name " +
                     "FROM players p " +
                     "JOIN users u ON p.user_id = u.id " +
                     "WHERE p.user_id = ?";
        try {
            Player player = jdbcTemplate.queryForObject(sql, playerRowMapper, userId);
            return Optional.ofNullable(player);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Player> findAll() {
        String sql = "SELECT p.*, u.email, u.first_name, u.last_name " +
                     "FROM players p " +
                     "JOIN users u ON p.user_id = u.id";
        return jdbcTemplate.query(sql, playerRowMapper);
    }

    public void update(Player player) {
        String sql = "UPDATE players SET position = ?, skill_level = ?, years_experience = ?, bio = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            player.getPosition(),
            player.getSkillLevel(),
            player.getYearsExperience(),
            player.getBio(),
            player.getId()
        );
    }

    public void delete(Long id) {
        String sql = "DELETE FROM players WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
