package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.managers.usable.RankManager;
import pl.d4nte.tools.managers.usable.TempRankManager;
import pl.d4nte.tools.utils.ChatUtils;

public class TempRankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("core.temprank")) {
            sender.sendMessage(ChatUtils.color("&cNie masz uprawnień!"));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /temprank <gracz> <ranga> <czas w sekundach>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color("&cGracz jest offline!"));
            return true;
        }

        String rank = args[1];
        if (!RankManager.rankExists(rank)) {
            sender.sendMessage(ChatUtils.color("&cRanga nie istnieje!"));
            return true;
        }

        try {
            long duration = Long.parseLong(args[2]);
            TempRankManager.setTempRank(target, rank, duration);
            sender.sendMessage(ChatUtils.color("&aPrzyznano graczowi " + target.getName() + " rangę " + rank + " na " + duration + " sekund."));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatUtils.color("&cNieprawidłowy czas!"));
        }

        return true;
    }
}
