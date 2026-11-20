package pl.d4nte.tools.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.d4nte.tools.managers.usable.RankManager;
import pl.d4nte.tools.managers.usable.VanishManager;
import pl.d4nte.tools.utils.ChatUtils;
import pl.d4nte.tools.utils.ConfigUtils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        List<String> motd = ConfigUtils.getConfig().getStringList("welcome");

        RankManager.applyPermissions(event.getPlayer(), RankManager.getRank(event.getPlayer()));

        for (Player vanished : VanishManager.getVanishedPlayers()) {
            if (!player.hasPermission("d4nte.tools.vanish")) {
                player.hidePlayer(vanished);
            }
        }

        if (motd.isEmpty()) return;

        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        String name = player.getName();

        for (String line : motd) {
            player.sendMessage(ChatUtils.color(line.replace("{TIME}", time)));
        }
    }
}