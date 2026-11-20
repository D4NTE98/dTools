package pl.d4nte.tools.listeners.other;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.d4nte.tools.utils.ChatUtils;

import java.util.ArrayList;
import java.util.List;

public class HelpListener implements Listener {

    private static FileConfiguration config;

    public static void setConfig(FileConfiguration config) {
        HelpListener.config = config;
    }

    public static void openHelpGUI(Player player) {
        Inventory helpInventory = Bukkit.createInventory(null, 27, ChatUtils.color(config.getString("pomoc.gui-title", "&aPomoc")));

        ConfigurationSection categories = config.getConfigurationSection("pomoc.kategorie");
        if (categories == null) {
            player.sendMessage(ChatUtils.color("&cBrak skonfigurowanych kategorii w pliku config.yml!"));
            return;
        }

        for (String key : categories.getKeys(false)) {
            String path = "pomoc.kategorie." + key;
            Material material = Material.matchMaterial(config.getString(path + ".icon", "BOOK"));
            if (material == null) material = Material.BOOK;

            ItemStack item = new ItemStack(material);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatUtils.color(config.getString(path + ".name", "&7" + key)));

                List<String> lore = new ArrayList<>();
                for (String line : config.getStringList(path + ".lore")) {
                    lore.add(ChatUtils.color(line));
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
            }

            int slot = config.getInt(path + ".slot", 0);
            helpInventory.setItem(slot, item);
        }

        player.openInventory(helpInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatUtils.color(config.getString("pomoc.gui-title", "&aPomoc")))) {
            event.setCancelled(true);
        }
    }
}
