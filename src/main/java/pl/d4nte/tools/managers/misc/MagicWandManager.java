package pl.d4nte.tools.managers.misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.meta.ItemMeta;
import pl.d4nte.tools.utils.ChatUtils;

import java.util.Arrays;

public class MagicWandManager implements Listener {
    private final JavaPlugin plugin;
    private static final String WAND_NAME = ChatUtils.color("&3&lMagiczna Różdżka");

    public MagicWandManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static ItemStack createMagicWand() {
        ItemStack wand = new ItemStack(Material.STICK);
        ItemMeta meta = wand.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(WAND_NAME);
            meta.setLore(Arrays.asList(ChatUtils.color("&7Kliknij, aby teleportować się na spawn!")));
            wand.setItemMeta(meta);
        }
        return wand;
    }

    public void registerRecipe() {
        NamespacedKey key = new NamespacedKey(plugin, "magic_wand");
        ShapedRecipe recipe = new ShapedRecipe(key, createMagicWand());

        recipe.shape("GGG", "GEG", "GGG");
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('E', Material.ENDER_PEARL);

        Bukkit.addRecipe(recipe);
    }

    @EventHandler
    public void onWandUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.STICK && item.hasItemMeta() && WAND_NAME.equals(item.getItemMeta().getDisplayName())) {
            event.setCancelled(true);

            player.sendMessage(ChatUtils.color("&3Teleportacja na spawn za 5 sekund... Nie ruszaj się!"));

            new BukkitRunnable() {
                @Override
                public void run() {
                    player.teleport(player.getWorld().getSpawnLocation());
                    player.sendMessage(ChatUtils.color("&aTeleportacja zakończona!"));

                    player.getInventory().removeItem(item);
                }
            }.runTaskLater(plugin, 100L); // 5 sekund = 100 ticków
        }
    }
}
