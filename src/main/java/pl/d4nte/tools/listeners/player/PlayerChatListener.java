package pl.d4nte.tools.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.eclipse.sisu.Priority;
import pl.d4nte.tools.managers.usable.RankManager;
import pl.d4nte.tools.managers.usable.UserManager;
import pl.d4nte.tools.managers.objects.User;
import pl.d4nte.tools.utils.ChatUtils;

public class PlayerChatListener implements Listener {

    private final UserManager userManager;

    public PlayerChatListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        User u = userManager.getUser(p.getUniqueId(), p.getName());
        if (u.isMuted()) {
            p.sendMessage(ChatUtils.color("&4&l⚠ &cJesteś wyciszony/a!"));
            event.setCancelled(true);
        }

        String rank = RankManager.getRank(event.getPlayer());
        String prefix = RankManager.getPrefix(rank);
        event.setFormat(ChatUtils.color(prefix + " " + event.getPlayer().getName() + ": &7" + event.getMessage()));
    }
}
