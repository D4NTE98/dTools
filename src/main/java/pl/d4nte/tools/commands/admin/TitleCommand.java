package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.utils.ChatUtils;

public class TitleCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.title")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.title&8)"));
            return false;
        }
        if (args.length < 3) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /title <gracz> <tytuł> <podtytuł>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color("&cTen gracz jest offline!"));
            return true;
        }

        String title = ChatUtils.color(args[1]);
        String subtitle = ChatUtils.color(args[2]);
        target.sendTitle(title, subtitle, 10, 70, 20);
        sender.sendMessage(ChatUtils.color("&aWysłano tytuł do &e" + target.getName()));
        return true;
    }
}
