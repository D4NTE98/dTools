package pl.d4nte.tools.managers.basic;

import pl.d4nte.tools.managers.basic.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class PunishmentManager {

    public static void addPunishment(UUID uuid, String playerName, String type, String reason, String admin, long expiry) {
        String sql = "INSERT INTO d4nte_tools_punishments (player_uuid, player_name, type, reason, admin, expiry, active) VALUES (?, ?, ?, ?, ?, ?, 1)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            stmt.setString(2, playerName);
            stmt.setString(3, type);
            stmt.setString(4, reason);
            stmt.setString(5, admin);
            stmt.setLong(6, expiry);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removePunishment(UUID uuid, String type) {
        String sql = "UPDATE d4nte_tools_punishments SET active = 0 WHERE player_uuid = ? AND type = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid.toString());
            stmt.setString(2, type);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
