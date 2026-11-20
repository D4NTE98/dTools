package pl.d4nte.tools.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.d4nte.tools.ToolsPlugin;

import java.io.File;

public class ConfigUtils {
    public static FileConfiguration getConfig() {
        return ToolsPlugin.getInstance().getConfig();
    }

    private static File dropFile;
    private static FileConfiguration dropConfig;

    public static void loadConfigs() {
        ToolsPlugin plugin = ToolsPlugin.getInstance();

        dropFile = new File(ToolsPlugin.getInstance().getDataFolder(), "drop.yml");
        if (!dropFile.exists()) {
            ToolsPlugin.getInstance().saveResource("drop.yml", false);
        }
        dropConfig = YamlConfiguration.loadConfiguration(dropFile);
    }

    public static FileConfiguration getDropConfig() {
        return dropConfig;
    }
}