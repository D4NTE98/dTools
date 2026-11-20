package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class KickCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.kick")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.kick&8)"));
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /kick <gracz>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color("&cTen gracz jest offline!"));
            return true;
        }

        target.kickPlayer(ChatUtils.color("&cZostałeś wyrzucony z serwera!"));
        sender.sendMessage(ChatUtils.color("&aWyrzucono gracza &e" + target.getName()));
        return true;
    }
}
