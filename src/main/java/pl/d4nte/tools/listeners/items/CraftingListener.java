package pl.d4nte.tools.listeners.items;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import pl.d4nte.tools.utils.ConfigUtils;

import java.util.EnumSet;
import java.util.Set;

public class CraftingListener implements Listener {
    private static boolean blockDiamondArmor;
    private static boolean blockNetheriteArmor;
    private static boolean blockNetheriteTools;

    private static final Set<Material> DIAMOND_ARMOR = EnumSet.of(
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE,
            Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS
    );

    private static final Set<Material> NETHERITE_ARMOR = EnumSet.of(
            Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE,
            Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS
    );

    private static final Set<Material> NETHERITE_TOOLS = EnumSet.of(
            Material.NETHERITE_SWORD, Material.NETHERITE_PICKAXE,
            Material.NETHERITE_AXE, Material.NETHERITE_SHOVEL,
            Material.NETHERITE_HOE
    );

    public static void loadConfig() {
        FileConfiguration config = ConfigUtils.getConfig();
        blockDiamondArmor = config.getBoolean("restrictions.disable-diamond-armor", true);
        blockNetheriteArmor = config.getBoolean("restrictions.disable-netherite-armor", true);
        blockNetheriteTools = config.getBoolean("restrictions.disable-netherite-tools", true);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        Material item = event.getRecipe().getResult().getType();

        if (blockDiamondArmor && DIAMOND_ARMOR.contains(item)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cCraftowanie diamentowej zbroi jest zablokowane!");
        } else if (blockNetheriteArmor && NETHERITE_ARMOR.contains(item)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cCraftowanie netheritowej zbroi jest zablokowane!");
        } else if (blockNetheriteTools && NETHERITE_TOOLS.contains(item)) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("§cCraftowanie netheritowych narzędzi i miecza jest zablokowane!");
        }
    }
}
