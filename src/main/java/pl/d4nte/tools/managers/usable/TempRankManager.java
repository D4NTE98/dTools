package pl.d4nte.tools.managers.usable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.d4nte.tools.managers.basic.DatabaseManager;
import pl.d4nte.tools.utils.ChatUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TempRankManager {

    public static void setTempRank(Player player, String rank, long durationInSeconds) {
        UUID uuid = player.getUniqueId();
        long expiryTime = System.currentTimeMillis() + (durationInSeconds * 1000);

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO d4nte_tools_temp_ranks (uuid, rank, expiry) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE rank=?, expiry=?");
            ps.setString(1, uuid.toString());
            ps.setString(2, rank);
            ps.setLong(3, expiryTime);
            ps.setString(4, rank);
            ps.setLong(5, expiryTime);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        RankManager.setRank(player, rank);
        player.sendMessage(ChatUtils.color("&aPrzyznano tymczasową rangę: &e" + rank + " &ana czas: &e" + formatTime(durationInSeconds)));
    }

    public static void checkTempRanks() {
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT uuid, rank, expiry FROM d4nte_tools_temp_ranks WHERE expiry <= ?");
            ps.setLong(1, System.currentTimeMillis());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("uuid"));
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    RankManager.setRank(player, RankManager.getDefaultRank());
                    player.sendMessage(ChatUtils.color("&cTwoja tymczasowa ranga wygasła. Przywrócono domyślną rangę."));
                }

                PreparedStatement delete = conn.prepareStatement("DELETE FROM d4nte_tools_temp_ranks WHERE uuid = ?");
                delete.setString(1, uuid.toString());
                delete.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatTime(long seconds) {
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) % 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) % 60;
        long sec = seconds % 60;
        return String.format("%d dni, %d godz., %d min., %d sek.", days, hours, minutes, sec);
    }
}
