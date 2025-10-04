package com.example.h2h_project.repository;

import com.example.h2h_project.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
}
