package pl.d4nte.tools.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.d4nte.tools.managers.basic.PunishmentManager;
import pl.d4nte.tools.managers.usable.UserManager;
import pl.d4nte.tools.utils.ChatUtils;

import java.util.UUID;

public class BanCommand implements CommandExecutor {
    private final UserManager userManager;

    public BanCommand(UserManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("d4nte.tools.ban")) {
            sender.sendMessage(ChatUtils.color("&4&l⚠ &cBrak uprawnień! &8(&7d4nte.tools.ban&8)"));
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatUtils.color("&cUżycie: /ban <gracz> <powód>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatUtils.color("&cGracz jest offline!"));
            return true;
        }

        UUID uuid = target.getUniqueId();
        String reason = String.join(" ", args).substring(args[0].length() + 1);
        String admin = sender.getName();

        PunishmentManager.addPunishment(uuid, target.getName(), "BAN", reason, admin, 0);
        target.kickPlayer(ChatUtils.color("&cZostałeś zbanowany! Powód: " + reason));

        Bukkit.broadcastMessage(ChatUtils.color("&e" + target.getName() + " &czostał zbanowany! Powód: " + reason));
        return true;
    }
}
