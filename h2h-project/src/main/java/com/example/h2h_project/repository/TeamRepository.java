package com.example.h2h_project.repository;

import com.example.h2h_project.model.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class TeamRepository {

    private final JdbcTemplate jdbcTemplate;

    public TeamRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createTeam(Team team) {
        String sql = "INSERT INTO teams (name, captain_id, zip_code, stadium, description, logo_url, home_color, away_color, max_roster_size, current_roster_size, wins, draws, losses, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, team.getName());
            ps.setLong(2, team.getCaptainId());
            ps.setString(3, team.getZipCode());
            ps.setString(4, team.getStadium());
            ps.setString(5, team.getDescription());
            ps.setString(6, team.getLogoUrl());
            ps.setString(7, team.getHomeColor());
            ps.setString(8, team.getAwayColor());
            ps.setInt(9, team.getMaxRosterSize());
            ps.setInt(10, team.getCurrentRosterSize());
            ps.setInt(11, team.getWins());
            ps.setInt(12, team.getDraws());
            ps.setInt(13, team.getLosses());
            ps.setTimestamp(14, Timestamp.valueOf(team.getCreatedAt()));
            ps.setTimestamp(15, Timestamp.valueOf(team.getUpdatedAt()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Team getTeamById(Long teamId) {
        String sql = "SELECT * FROM TEAMS WHERE ID = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{teamId}, (rs, rowNum) -> {
            Team team = new Team();
            team.setId(rs.getLong("id"));
            team.setName(rs.getString("name"));
            team.setCaptainId(rs.getLong("captain_id"));
            team.setZipCode(rs.getString("zip_code"));
            team.setStadium(rs.getString("stadium"));
            team.setDescription(rs.getString("description"));
            team.setLogoUrl(rs.getString("logo_url"));
            team.setHomeColor(rs.getString("home_color"));
            team.setAwayColor(rs.getString("away_color"));
            team.setMaxRosterSize(rs.getInt("max_roster_size"));
            team.setCurrentRosterSize(rs.getInt("current_roster_size"));
            team.setWins(rs.getInt("wins"));
            team.setDraws(rs.getInt("draws"));
            team.setLosses(rs.getInt("losses"));
            team.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            team.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return team;
        });

    }
}

