package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class TphereCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.tphere")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.tphere&8)"));
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cTylko gracze mogą używać tej komendy!"));
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /tphere <gracz>"));
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatUtils.color("&cTen gracz jest offline!"));
            return true;
        }

        target.teleport(player.getLocation());
        player.sendMessage(ChatUtils.color("&aTeleportowano gracza &e" + target.getName() + " &ado Ciebie."));
        return true;
    }
}
