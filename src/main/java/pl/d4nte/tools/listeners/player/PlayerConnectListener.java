package pl.d4nte.tools.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import pl.d4nte.tools.managers.usable.UserManager;
import pl.d4nte.tools.managers.objects.User;
import pl.d4nte.tools.utils.ChatUtils;

public class PlayerConnectListener implements Listener {
    private final UserManager userManager;

    public PlayerConnectListener(UserManager userManager) {
        this.userManager = userManager;
    }

    @EventHandler
    public void onPlayerConnect(PlayerLoginEvent e) {
        Player p = (Player) e.getPlayer();
        User u = userManager.getUser(p.getUniqueId(), p.getName());
        if (u.isBanned())
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatUtils.color("&4&l⚠ &cJesteś zbanowany/a na tym serwerze!\n\n&3&lⓘ &bOdwołaj się na forum: &3&nhttps://udantego.pl"));
    }
}
