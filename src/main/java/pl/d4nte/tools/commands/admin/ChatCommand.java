package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.d4nte.tools.utils.ChatUtils;

public class ChatCommand implements CommandExecutor {
    private static boolean chatEnabled = true;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.chat")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.chat&8)"));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /chat <clear/on/off>"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "clear":
                for (int i = 0; i < 100; i++) {
                    Bukkit.broadcastMessage("");
                }
                Bukkit.broadcastMessage(ChatUtils.color("&aCzat został wyczyszczony!"));
                break;
            case "on":
                chatEnabled = true;
                Bukkit.broadcastMessage(ChatUtils.color("&aCzat został włączony!"));
                break;
            case "off":
                chatEnabled = false;
                Bukkit.broadcastMessage(ChatUtils.color("&cCzat został wyłączony!"));
                break;
            default:
                sender.sendMessage(ChatUtils.color("&cNieznana opcja! Użyj: /chat <clear/on/off>"));
                break;
        }
        return true;
    }

    public static boolean isChatEnabled() {
        return chatEnabled;
    }
}
