package pl.d4nte.tools;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.d4nte.tools.commands.D4ToolsCommand;
import pl.d4nte.tools.commands.admin.*;
import pl.d4nte.tools.commands.user.DropCommand;
import pl.d4nte.tools.commands.user.HelpCommand;
import pl.d4nte.tools.commands.user.MsgCommand;
import pl.d4nte.tools.listeners.items.CraftingListener;
import pl.d4nte.tools.listeners.other.CreativeItemTagger;
import pl.d4nte.tools.listeners.other.HelpListener;
import pl.d4nte.tools.listeners.player.PlayerChatListener;
import pl.d4nte.tools.listeners.player.PlayerConnectListener;
import pl.d4nte.tools.listeners.player.PlayerJoinListener;
import pl.d4nte.tools.listeners.system.TabBlockerListener;
import pl.d4nte.tools.managers.basic.DatabaseManager;
import pl.d4nte.tools.managers.misc.AutoMessageManager;
import pl.d4nte.tools.managers.misc.DropManager;
import pl.d4nte.tools.managers.misc.MagicWandManager;
import pl.d4nte.tools.managers.misc.TablistManager;
import pl.d4nte.tools.managers.usable.RankManager;
import pl.d4nte.tools.managers.usable.TempRankManager;
import pl.d4nte.tools.managers.usable.UserManager;
import pl.d4nte.tools.utils.ChatUtils;
import pl.d4nte.tools.utils.ConfigUtils;
import pl.d4nte.tools.utils.gui.DropGUI;

public class ToolsPlugin extends JavaPlugin {

    private DatabaseManager databaseManager;
    private UserManager userManager;

    private static ToolsPlugin instance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;

        ConfigUtils.loadConfigs();
        HelpListener.setConfig(getConfig());

        databaseManager = new DatabaseManager(this);
        userManager = new UserManager(databaseManager);

        registerCommands();
        registerEvents();

        MagicWandManager wandManager = new MagicWandManager(this);
        wandManager.registerRecipe();

        TablistManager.startAnimation();

        AutoMessageManager.loadMessages();
        AutoMessageManager.startBroadcasting();

        DropManager.loadDrops();
        DropGUI.loadGUI();

        CraftingListener.loadConfig();

        RankManager.loadRanks(getConfig());
        RankManager.applyPermissionsToAllPlayers();

        Bukkit.getScheduler().runTaskTimer(this, TempRankManager::checkTempRanks, 20L, 600L);

        getLogger().info(ChatUtils.color("&ad4nte_tools został załadowany."));
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerConnectListener(userManager), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new TabBlockerListener(), this);
        pm.registerEvents(new PlayerChatListener(userManager), this);
        pm.registerEvents(new MagicWandManager(this), this);
        pm.registerEvents(new CraftingListener(), this);
        pm.registerEvents(new CreativeItemTagger(), this);
        pm.registerEvents(new DropManager(), this);
        pm.registerEvents(new DropGUI(), this);
        pm.registerEvents(new HelpListener(), this);
    }

    private void registerCommands() {
        getCommand("d4tools").setExecutor(new D4ToolsCommand());
        getCommand("ban").setExecutor(new BanCommand(userManager));
        getCommand("unban").setExecutor(new UnbanCommand(userManager));
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("mute").setExecutor(new MuteCommand(userManager));
        getCommand("unmute").setExecutor(new UnmuteCommand(userManager));
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("tp").setExecutor(new TpCommand());
        getCommand("tphere").setExecutor(new TphereCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("title").setExecutor(new TitleCommand());
        getCommand("chat").setExecutor(new ChatCommand());
        getCommand("msg").setExecutor(new MsgCommand());
        getCommand("drop").setExecutor(new DropCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("temprank").setExecutor(new TempRankCommand());
        getCommand("pomoc").setExecutor(new HelpCommand());
        getCommand("rank").setExecutor(new RankCommand());
    }

    @Override
    public void onDisable() {
        databaseManager.closeConnection();
    }

    public static ToolsPlugin getInstance() {
        return instance;
    }
}