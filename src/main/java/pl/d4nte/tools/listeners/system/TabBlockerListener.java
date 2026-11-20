package pl.d4nte.tools.listeners.system;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.TabCompleteEvent;

public class TabBlockerListener implements Listener {
    @EventHandler
    public void onTabComplete(TabCompleteEvent event) {
        if (!event.getSender().hasPermission("d4nte.tools.bypass")) {
            event.setCancelled(true);
        }
    }
}