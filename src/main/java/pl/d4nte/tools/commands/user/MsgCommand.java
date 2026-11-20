package pl.d4nte.tools.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class MsgCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.msg")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.msg&8)"));
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatUtils.color("&cTylko gracze mogą używać tej komendy!"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /msg <gracz> <wiadomość>"));
            return true;
        }

        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(ChatUtils.color("&cTen gracz jest offline!"));
            return true;
        }

        String message = String.join(" ", args).substring(args[0].length() + 1);

        player.sendMessage(ChatUtils.color("&6[Ty -> " + target.getName() + "] &f" + message));
        target.sendMessage(ChatUtils.color("&6[" + player.getName() + " -> Ty] &f" + message));
        return true;
    }
}
