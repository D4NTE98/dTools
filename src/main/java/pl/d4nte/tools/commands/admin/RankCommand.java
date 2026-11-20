package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.managers.usable.RankManager;
import pl.d4nte.tools.utils.ChatUtils;

public class RankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.rank")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.rank&8)"));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /rank set <gracz> <ranga>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color("&cGracz nie jest online!"));
            return true;
        }

        String rank = args[2];
        RankManager.setRank(target, rank);
        sender.sendMessage(ChatUtils.color("&aUstawiono rangę &e" + rank + " &adla &e" + target.getName()));
        return true;
    }
}
