package pl.d4nte.tools.managers.usable;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import pl.d4nte.tools.managers.basic.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class RankManager {
    private static final Set<String> availableRanks = new HashSet<>();
    private static final Map<UUID, PermissionAttachment> playerPermissions = new HashMap<>();
    private static final Map<String, List<String>> rankPermissions = new HashMap<>();
    private static final Map<String, String> rankPrefixes = new HashMap<>();
    private static String defaultRank = "Gracz";

    public static void loadRanks(FileConfiguration config) {
        availableRanks.clear();
        rankPermissions.clear();
        rankPrefixes.clear();

        if (config.contains("rangi")) {
            for (String rank : config.getConfigurationSection("rangi").getKeys(false)) {
                availableRanks.add(rank);
                rankPermissions.put(rank, config.getStringList("rangi." + rank + ".permissions"));
                rankPrefixes.put(rank, config.getString("rangi." + rank + ".prefix", ""));
            }
        }

        defaultRank = config.getString("domyslna_ranga", "Gracz");
    }

    public static boolean rankExists(String rank) {
        return availableRanks.contains(rank);
    }

    public static String getDefaultRank() {
        return defaultRank;
    }

    public static String getRank(Player player) {
        UUID uuid = player.getUniqueId();
        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT rank FROM d4nte_tools_users WHERE uuid = ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("rank");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultRank;
    }

    public static void setRank(Player player, String rank) {
        UUID uuid = player.getUniqueId();
        if (!rankExists(rank)) {
            player.sendMessage("§cTaka ranga nie istnieje!");
            return;
        }

        try (Connection conn = DatabaseManager.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE d4nte_tools_users SET rank = ? WHERE uuid = ?");
            ps.setString(1, rank);
            ps.setString(2, uuid.toString());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        applyPermissions(player, rank);
        player.sendMessage("§aTwoja ranga została zmieniona na: §e" + rank);
    }

    public static String getPrefix(String rank) {
        return rankPrefixes.getOrDefault(rank, "");
    }

    public static void applyPermissions(Player player, String rank) {
        UUID uuid = player.getUniqueId();
        if (playerPermissions.containsKey(uuid)) {
            PermissionAttachment attachment = playerPermissions.get(uuid);
            attachment.getPermissions().clear();
        } else {
            playerPermissions.put(uuid, player.addAttachment(Bukkit.getPluginManager().getPlugin("d4nte_tools")));
        }

        PermissionAttachment attachment = playerPermissions.get(uuid);
        List<String> permissions = rankPermissions.getOrDefault(rank, new ArrayList<>());

        for (String perm : permissions) {
            boolean value = !perm.startsWith("-");
            attachment.setPermission(value ? perm : perm.substring(1), value);
        }

        player.recalculatePermissions();
    }

    public static void applyPermissionsToAllPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String rank = getRank(player);
            applyPermissions(player, rank);
        }
    }
}
