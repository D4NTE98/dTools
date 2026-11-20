package pl.d4nte.tools.managers.usable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VanishManager {
    private static final Set<Player> vanishedPlayers = new HashSet<>();

    public static void toggleVanish(Player player) {
        if (vanishedPlayers.contains(player)) {
            disableVanish(player);
        } else {
            enableVanish(player);
        }
    }

    public static void enableVanish(Player player) {
        vanishedPlayers.add(player);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("d4nte.tools.vanish")) {
                onlinePlayer.hidePlayer(player);
            }
        }
        player.sendMessage("§aTryb Vanish został włączony.");
    }

    public static void disableVanish(Player player) {
        vanishedPlayers.remove(player);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.showPlayer(player);
        }
        player.sendMessage("§cTryb Vanish został wyłączony.");
    }

    public static boolean isVanished(Player player) {
        return vanishedPlayers.contains(player);
    }

    public static Set<Player> getVanishedPlayers() {
        return Collections.unmodifiableSet(vanishedPlayers);
    }
}
