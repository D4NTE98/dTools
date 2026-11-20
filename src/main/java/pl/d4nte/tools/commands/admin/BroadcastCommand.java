package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.d4nte.tools.utils.ChatUtils;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.broadcast")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.broadcast&8)"));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /broadcast <wiadomość>"));
            return true;
        }

        String message = String.join(" ", args);
        Bukkit.broadcastMessage(ChatUtils.color("&6[Broadcast] &f" + message));
        return true;
    }
}
