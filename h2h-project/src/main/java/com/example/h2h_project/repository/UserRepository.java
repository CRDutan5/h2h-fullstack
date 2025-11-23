package com.example.h2h_project.repository;

import com.example.h2h_project.model.User;
import com.example.h2h_project.model.UserRole;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createUser(User user) {
        String sql = "INSERT INTO users (email, password, first_name, last_name, zip_code, role, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        var keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getZipCode());
            ps.setString(6, user.getRole().name());
            ps.setTimestamp(7, java.sql.Timestamp.valueOf(user.getCreatedAt()));
            ps.setTimestamp(8, java.sql.Timestamp.valueOf(user.getUpdatedAt()));
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
            User user = jdbcTemplate.queryForObject(sql, new Object[]{email}, (rs, rowNum) -> {
                User u = new User();
                u.setId(rs.getLong("id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
                u.setZipCode(rs.getString("zip_code"));
                u.setRole(UserRole.fromString(rs.getString("role")));
                u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                u.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                return u;
            });
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
