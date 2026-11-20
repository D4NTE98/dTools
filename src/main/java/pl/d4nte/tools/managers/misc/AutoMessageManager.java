package pl.d4nte.tools.managers.misc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.d4nte.tools.ToolsPlugin;
import pl.d4nte.tools.utils.ChatUtils;
import pl.d4nte.tools.utils.ConfigUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoMessageManager {
    private static List<String> messages;
    private static int interval;
    private static final AtomicInteger index = new AtomicInteger(0);

    public static void loadMessages() {
        FileConfiguration config = ConfigUtils.getConfig();
        messages = config.getStringList("auto-messages.messages");
        interval = config.getInt("auto-messages.interval", 60);
    }

    public static void startBroadcasting() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (messages.isEmpty()) return;

                String message = messages.get(index.getAndIncrement() % messages.size());
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.sendMessage(ChatUtils.color(message.replace("{PLAYER}", player.getName())));
                }
            }
        }.runTaskTimerAsynchronously(ToolsPlugin.getInstance(), 0, interval * 20L);
    }
}
