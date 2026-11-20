package pl.d4nte.tools.managers.misc;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pl.d4nte.tools.ToolsPlugin;
import pl.d4nte.tools.utils.ChatUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablistManager {
    private static final List<String> HEADER_ANIMATION = Arrays.asList(
            "&bu",
            "&buD",
            "&buDa",
            "&buDan",
            "&buDant",
            "&buDante",
            "&buDanteg",
            "&buDantego",
            "&buDanteg",
            "&buDante",
            "&buDant",
            "&buDan",
            "&buDa",
            "&buD",
            "&bu"
    );

    private static final Map<Integer, String> MONTHS = new HashMap<>();
    static {
        MONTHS.put(1, "stycznia");
        MONTHS.put(2, "lutego");
        MONTHS.put(3, "marca");
        MONTHS.put(4, "kwietnia");
        MONTHS.put(5, "maja");
        MONTHS.put(6, "czerwca");
        MONTHS.put(7, "lipca");
        MONTHS.put(8, "sierpnia");
        MONTHS.put(9, "września");
        MONTHS.put(10, "października");
        MONTHS.put(11, "listopada");
        MONTHS.put(12, "grudnia");
    }

    private static int frame = 0;

    public static void startAnimation() {
        new BukkitRunnable() {
            @Override
            public void run() {
                String header = HEADER_ANIMATION.get(frame % HEADER_ANIMATION.size());

                updateTablist(header);
                frame++;
            }
        }.runTaskTimer(ToolsPlugin.getInstance(), 0, 10L);
    }

    private static void updateTablist(String header) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            String time = new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis());
            LocalDate now = LocalDate.now();
            String date = now.getDayOfMonth() + " " + MONTHS.get(now.getMonthValue()) + " " + now.getYear();
            int onlinePlayers = Bukkit.getOnlinePlayers().size();
            double tps = Bukkit.getTPS()[0];

            int ping = player.getPing();
            String footer = "&7Czas: &b{TIME} &7| Data: &b{DATE}\n"
                    + "&7Graczy online: &b{ONLINE-PLAYERS}\n"
                    + "&7Twój ping: &b{PING}ms &7| Serwer TPS: &b{TPS}";

            footer = footer.replace("{TIME}", time)
                    .replace("{DATE}", date)
                    .replace("{ONLINE-PLAYERS}", String.valueOf(onlinePlayers))
                    .replace("{PING}", String.valueOf(ping))
                    .replace("{TPS}", String.format("%.2f", tps));
            player.setPlayerListHeaderFooter(
                    new TextComponent(ChatUtils.color(header)),
                    new TextComponent(ChatUtils.color(footer))
            );
        }
    }
}