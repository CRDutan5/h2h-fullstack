package com.example.h2h_project.repository;

import com.example.h2h_project.dto.PlayerDTO;
import com.example.h2h_project.model.Player;
import com.example.h2h_project.model.Position;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long createPlayer(Player player) {
        String sql = "INSERT INTO players (user_id, position, team_id) VALUES (?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            var ps = connection.prepareStatement(sql, new String[]{"id"});

            // Set user_id
            ps.setLong(1, player.getUserId());

            // Set position
            ps.setString(2, player.getPosition().name());

            // Set team_id (nullable)
            if (player.getTeamId() != null) {
                ps.setLong(3, player.getTeamId());
            } else {
                ps.setNull(3, java.sql.Types.BIGINT);
            }

            return ps;
        }, keyHolder);


        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    public List<PlayerDTO> getPlayersByTeamId(Long teamId) {
        String sql = """
                SELECT p.id AS player_id, p.user_id, p.position, u.first_name, u.last_name
                FROM players p
                JOIN users u ON p.user_id = u.id
                WHERE p.team_id = ?
                """;
        return jdbcTemplate.query(sql, new Object[]{teamId}, (rs, rowNum) -> {
            PlayerDTO playerDTO = new PlayerDTO();
            playerDTO.setPlayerId(rs.getLong("player_id"));
            playerDTO.setUserId(rs.getLong("user_id"));
            playerDTO.setPosition(Position.fromString(rs.getString("position")));
            playerDTO.setFirstName(rs.getString("first_name"));
            playerDTO.setLastName(rs.getString("last_name"));
            return playerDTO;
        });
    }

    public boolean updatePlayerTeamId(Long playerId, Long teamId) {
        String sql = "UPDATE players SET team_id = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, teamId, playerId);
        return rowsAffected > 0;
    }

    public int countPlayersByTeamId(Long teamId) {
        String sql = "SELECT COUNT(*) FROM players WHERE team_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, teamId);
        return count != null ? count : 0;
    }

    public Player getPlayerById(Long playerId) {
        String sql = "SELECT * FROM players WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{playerId}, (rs, rowNum) -> {
            Player player = new Player();
            player.setId(rs.getLong("id"));
            player.setUserId(rs.getLong("user_id"));
            player.setTeamId(rs.getObject("team_id", Long.class));
            player.setPosition(Position.fromString(rs.getString("position")));
            return player;
        });
    }

}
