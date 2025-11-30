package com.example.h2h_project.repository;

import com.example.h2h_project.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setZipCode(rs.getString("zip_code"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    };

    public Long createUser(User user) {
        String sql = "INSERT INTO users (email, password, first_name, last_name, zip_code, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getZipCode());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(user.getUpdatedAt()));
            return ps;
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            user.setId(keyHolder.getKey().longValue());
        }

        return user.getId();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, email);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
