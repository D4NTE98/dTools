package pl.d4nte.tools.managers.misc;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.d4nte.tools.utils.ChatUtils;
import pl.d4nte.tools.utils.ConfigUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DropManager implements Listener {
    private static final Map<Material, DropInfo> dropChances = new HashMap<>();
    private static final Random random = new Random();

    public static void loadDrops() {
        FileConfiguration config = ConfigUtils.getDropConfig();
        dropChances.clear();

        for (String key : config.getConfigurationSection("drops").getKeys(false)) {
            try {
                Material material = Material.valueOf(key);
                double chance = config.getDouble("drops." + key + ".chance");
                int amount = config.getInt("drops." + key + ".amount");
                dropChances.put(material, new DropInfo(chance, amount));
            } catch (IllegalArgumentException e) {
                System.out.println("Nieprawidłowy materiał w drop.yml: " + key);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.STONE) return;

        Player player = event.getPlayer();
        for (Map.Entry<Material, DropInfo> entry : dropChances.entrySet()) {
            Material material = entry.getKey();
            DropInfo dropInfo = entry.getValue();

            if (random.nextDouble() * 100 < dropInfo.chance) {
                ItemStack item = new ItemStack(material, dropInfo.amount);

                Map<Integer, ItemStack> notAdded = player.getInventory().addItem(item);
                if (!notAdded.isEmpty()) {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
                }

                //event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
                player.sendActionBar(ChatUtils.color("&aWydropiłeś: &f" + material.name().toLowerCase().replace("_", " ")));
            }
        }
    }

    private static class DropInfo {
        double chance;
        int amount;

        public DropInfo(double chance, int amount) {
            this.chance = chance;
            this.amount = amount;
        }
    }
}
