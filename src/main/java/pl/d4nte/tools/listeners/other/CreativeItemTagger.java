package pl.d4nte.tools.listeners.other;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreativeItemTagger implements Listener {

    private final String tag = " §7[z GM1]";

    @EventHandler
    public void onItemMove(InventoryClickEvent event) {
        if (event.getWhoClicked().getGameMode() != GameMode.CREATIVE) return;
        ItemStack item = event.getCurrentItem();
        if (item != null) tagItem(item);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        tagItem(event.getItemDrop().getItemStack());
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event) {
        if (event.getPlayer().getGameMode() != GameMode.CREATIVE) return;
        tagItem(event.getItem().getItemStack());
    }

    private void tagItem(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) return;
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(item.getType().name().toLowerCase().replace("_", " ") + tag);
            item.setItemMeta(meta);
        }
    }
}
