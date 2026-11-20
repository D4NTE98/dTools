package pl.d4nte.tools.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.d4nte.tools.utils.ChatUtils;
import pl.d4nte.tools.utils.ConfigUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DropGUI implements Listener {
    private static Inventory dropInventory;

    public static void loadGUI() {
        dropInventory = Bukkit.createInventory(null, 27, ChatUtils.color("&bDropy ze stone"));

        FileConfiguration config = ConfigUtils.getDropConfig();
        for (String key : config.getConfigurationSection("drops").getKeys(false)) {
            try {
                Material material = Material.valueOf(key);
                double chance = config.getDouble("drops." + key + ".chance");

                ItemStack item = new ItemStack(material);
                ItemMeta meta = item.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(ChatUtils.color("&7Szansa na drop: &b" + chance + "%"));
                meta.setLore(lore);
                item.setItemMeta(meta);

                dropInventory.addItem(item);
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowy materiał w drop.yml: " + key);
            }
        }
    }

    public static void open(Player player) {
        player.openInventory(dropInventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(ChatUtils.color("&bDropy ze stone"))) {
            event.setCancelled(true);
        }
    }
}
